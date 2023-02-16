package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.adverts.ui.AdvertsAdapter
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentFavoritesBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import com.alibasoglu.ciftlikpazarimandroid.utils.lifecycle.observe
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {

    private val toolbarConfiguration = ToolbarConfiguration(R.string.favorites)

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val favoritesViewModel by viewModels<FavoritesViewModel>()

    private val advertsAdapterListener = object : AdvertsAdapter.AdvertsAdapterListener {
        override fun onAdvertClick(advert: Advert) {
            navToAdvertDetailsFragment(advert)
        }
    }

    private val advertsAdapter = AdvertsAdapter(advertsAdapterListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavBar()
        initUI()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        favoritesViewModel.getFavoriteAdverts()
    }

    private fun initUI() {
        with(binding) {
            favoritesRecyclerView.adapter = advertsAdapter
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.observe {
            favoritesViewModel.favoriteAdvertsState.collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { list ->
                            if (list.isEmpty()) {
                                with(binding) {
                                    favoritesRecyclerView.visibility = View.GONE
                                    progressBar.visibility = View.GONE
                                    emptyStatusTextView.visibility = View.VISIBLE
                                }
                            } else {
                                advertsAdapter.submitData(PagingData.from(list))
                                binding.progressBar.visibility = View.GONE
                            }
                        }
                    }
                    is Resource.Error -> {
                        binding.errorMessage.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        if (result.isLoading) {
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun navToAdvertDetailsFragment(advert: Advert) {
        nav(FavoritesFragmentDirections.actionFavoritesFragmentToAdvertDetailsFragment(advert))
    }
}
