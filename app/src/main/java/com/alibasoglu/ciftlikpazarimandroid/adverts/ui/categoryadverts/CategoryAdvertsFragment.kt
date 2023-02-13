package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.categoryadverts

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.adverts.ui.AdvertsAdapter
import com.alibasoglu.ciftlikpazarimandroid.adverts.ui.AdvertsLoadStateAdapter
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentCategoryAdvertsBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.lifecycle.observe
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryAdvertsFragment : BaseFragment(R.layout.fragment_category_adverts) {

    private val toolbarConfiguration = ToolbarConfiguration(
        startIconResId = R.drawable.ic_baseline_arrow_back_24,
        startIconClick = ::navBack,
    )
    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration = toolbarConfiguration)

    private val categoryAdvertsViewModel by viewModels<CategoryAdvertsViewModel>()

    private val advertsStateCollector = FlowCollector<PagingData<Advert>> {
        advertsAdapter.submitData(it)
    }

    private val advertsAdapterListener = object : AdvertsAdapter.CategoryAdvertsAdapterListener {
        override fun onAdvertClick(advert: Advert) {
            navToAdvertDetailsFragment(advert)
        }
    }

    private val advertsAdapter = AdvertsAdapter(advertsAdapterListener)

    private val binding by viewBinding(FragmentCategoryAdvertsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initAdvertsState()
        initObservers()
    }

    private fun initUi() {
        hideBottomNavBar()
        getToolbar()?.setTitle(categoryAdvertsViewModel.getCategoryName())
        with(binding) {
            advertsRecyclerView.adapter = advertsAdapter.withLoadStateFooter(
                footer = AdvertsLoadStateAdapter { advertsAdapter.retry() }
            )
            retryButton.setOnClickListener { advertsAdapter.retry() }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.observe {
            categoryAdvertsViewModel.categoryAdvertsState.collect(advertsStateCollector)
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

    private fun navToAdvertDetailsFragment(advert: Advert) {
        nav(CategoryAdvertsFragmentDirections.actionCategoryAdvertsFragmentToAdvertDetailsFragment(advert))
    }

}
