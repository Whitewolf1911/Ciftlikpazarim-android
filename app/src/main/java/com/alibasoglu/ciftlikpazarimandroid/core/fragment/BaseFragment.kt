package com.alibasoglu.ciftlikpazarimandroid.core.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.alibasoglu.ciftlikpazarimandroid.MainActivity
import com.alibasoglu.ciftlikpazarimandroid.customviews.CustomToolbar

abstract class BaseFragment(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

    abstract val fragmentConfiguration: FragmentConfiguration

    private val mainActivity
        get() = activity as? MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customizeFragment()
    }

    private fun customizeFragment() {
        mainActivity?.configureToolbar(fragmentConfiguration.toolbarConfiguration)
    }

    protected fun getToolbar(): CustomToolbar? {
        return mainActivity?.getToolbar()
    }

    protected fun navBack() {
        mainActivity?.navBack()
    }

    protected fun nav(directions: NavDirections, onError: (() -> Unit)? = null) {
        mainActivity?.nav(directions, onError)
    }
}
