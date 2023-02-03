package com.alibasoglu.ciftlikpazarimandroid

import android.os.Bundle
import android.view.View
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
            searchView.setIconifiedByDefault(false)
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

}
