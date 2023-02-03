package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.searchadverts

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.adverts.ui.AdvertsAdapter
import com.alibasoglu.ciftlikpazarimandroid.adverts.ui.AdvertsLoadStateAdapter
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentSearchAdvertsBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.lifecycle.observe
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchAdvertsFragment : BaseFragment(R.layout.fragment_search_adverts) {

    private val toolbarConfiguration = ToolbarConfiguration(
        titleResId = R.string.search_results,
        startIconResId = R.drawable.ic_baseline_arrow_back_24,
        startIconClick = ::navBack
    )
    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val searchAdvertsViewModel by viewModels<SearchAdvertsViewModel>()

    private val binding by viewBinding(FragmentSearchAdvertsBinding::bind)

    private val advertsAdapterListener = object : AdvertsAdapter.CategoryAdvertsAdapterListener {
        override fun onAdvertClick(advert: Advert) {
            navToAdvertDetailsFragment(advert)
        }
    }

    private val advertsAdapter = AdvertsAdapter(advertsAdapterListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initAdvertsState()
        initObservers()
    }

    private fun initUI() {
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.isVisible = false
        with(binding) {
            searchView.apply {
                setIconifiedByDefault(false)
                setQuery(searchAdvertsViewModel.getInitialSearchQuery(), false)
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null && query.isNotBlank()) {
                            searchAdvertsViewModel.getSearchedAdverts(query)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })
            }
            advertsRecyclerView.adapter = advertsAdapter.withLoadStateFooter(
                footer = AdvertsLoadStateAdapter { advertsAdapter.retry() }
            )
            retryButton.setOnClickListener { advertsAdapter.retry() }
        }
    }

    private fun initAdvertsState() {
        lifecycleScope.launch {
            advertsAdapter.loadStateFlow.collectLatest { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && advertsAdapter.itemCount == 0
                val isErrorOccurred = loadState.source.refresh is LoadState.Error
                val isLoading = loadState.source.refresh is LoadState.Loading
                with(binding) {
                    // show empty list indicator
                    advertsRecyclerView.isVisible = !isListEmpty
                    emptyStatusTextView.isVisible = isListEmpty

                    // show progress bar for loading state
                    progressBar.isVisible = isLoading

                    // show try again button if request fails
                    retryButton.isVisible = isErrorOccurred
                    errorMessage.isVisible = isErrorOccurred
                }
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.observe {
            searchAdvertsViewModel.searchAdvertsFlow.collectLatest {
                advertsAdapter.submitData(it)
            }
        }
    }

    private fun navToAdvertDetailsFragment(advert: Advert) {
        nav(SearchAdvertsFragmentDirections.actionSearchAdvertsFragmentToAdvertDetailsFragment(advert))
    }
}
