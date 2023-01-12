package com.alibasoglu.ciftlikpazarimandroid.core.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.alibasoglu.ciftlikpazarimandroid.MainActivity

abstract class BaseFragment(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

    abstract val fragmentConfiguration: FragmentConfiguration

    private val mainActivity
        get() = activity as? MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customizeFragment()
    }

    private fun customizeFragment() {
      //  mainActivity?.configureToolbar()
    }


}
