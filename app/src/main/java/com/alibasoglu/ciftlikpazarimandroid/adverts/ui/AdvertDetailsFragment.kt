package com.alibasoglu.ciftlikpazarimandroid.adverts.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentAdvertDetailsBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdvertDetailsFragment : BaseFragment(R.layout.fragment_advert_details) {

    private val toolbarConfiguration = ToolbarConfiguration(
        titleResId = R.string.advert_details,
        startIconResId = R.drawable.ic_baseline_arrow_back_24,
        startIconClick = ::navBack
    )

    private val binding by viewBinding(FragmentAdvertDetailsBinding::bind)

    private val advertDetailsViewModel by viewModels<AdvertDetailsViewModel>()

    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        // TODO here
    }

}
