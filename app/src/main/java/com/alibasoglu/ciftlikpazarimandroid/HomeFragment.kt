package com.alibasoglu.ciftlikpazarimandroid

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentHomeBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val toolbarConfiguration = ToolbarConfiguration(R.string.app_name)

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.isVisible = true
        with(binding) {
            searchView.apply {
                maxWidth = Int.MAX_VALUE
                setIconifiedByDefault(false)
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null && query.isNotBlank()) {
                            navToSearchAdvertsFragment(query)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })
            }
            cowButton.setOnClickListener {
                navToCategoryAdvertsFragment(R.string.cows)
            }
            sheepButton.setOnClickListener {
                navToCategoryAdvertsFragment(R.string.small_cattle)
            }
            chickenButton.setOnClickListener {
                navToCategoryAdvertsFragment(R.string.coop_animals)
            }
            beeButton.setOnClickListener {
                navToCategoryAdvertsFragment(R.string.bee_production)
            }
            farmButton.setOnClickListener {
                navToCategoryAdvertsFragment(R.string.farming)
            }
            machineButton.setOnClickListener {
                navToCategoryAdvertsFragment(R.string.machine)
            }
        }
    }

    private fun navToCategoryAdvertsFragment(categorySelectedRes: Int) {
        nav(HomeFragmentDirections.actionHomeFragmentToCategoryAdvertsFragment(getString(categorySelectedRes)))
    }

    private fun navToSearchAdvertsFragment(searchQuery: String) {
        nav(HomeFragmentDirections.actionHomeFragmentToSearchAdvertsFragment(searchQuery))
    }

}
