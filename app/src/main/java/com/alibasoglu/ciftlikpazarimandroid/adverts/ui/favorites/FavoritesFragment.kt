package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.favorites

import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {

    private val toolbarConfiguration = ToolbarConfiguration(R.string.favorites)

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

}
