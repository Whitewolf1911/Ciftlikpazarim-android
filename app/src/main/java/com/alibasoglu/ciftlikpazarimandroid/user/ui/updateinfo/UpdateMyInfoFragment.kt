package com.alibasoglu.ciftlikpazarimandroid.user.ui.updateinfo

import android.os.Bundle
import android.view.View
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentUpdateMyInfoBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateMyInfoFragment : BaseFragment(R.layout.fragment_update_my_info) {

    private val toolbarConfiguration = ToolbarConfiguration(
        titleResId = R.string.update_my_info,
        startIconResId = R.drawable.ic_baseline_arrow_back_24,
        startIconClick = ::navBack
    )

    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentUpdateMyInfoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        hideBottomNavBar()
        val viewPagerAdapter = UpdateInfoViewPagerAdapter(childFragmentManager)

        with(binding) {
            toolbar.configure(toolbarConfiguration)
            viewPager.adapter = viewPagerAdapter
            tabLayout.setupWithViewPager(viewPager)

        }

    }
}
