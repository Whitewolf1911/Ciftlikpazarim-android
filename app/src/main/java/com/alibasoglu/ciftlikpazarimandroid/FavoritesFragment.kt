package com.alibasoglu.ciftlikpazarimandroid

import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {

    private val toolbarConfiguration = ToolbarConfiguration(R.string.favorites)

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

}
