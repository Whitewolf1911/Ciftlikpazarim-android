package com.alibasoglu.ciftlikpazarimandroid.user.ui.updateinfo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class UpdateInfoViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val fragmentList = listOf(
        ChangeUserInfoFragment(),
        ChangePasswordFragment()
    )
    private val titleList = listOf(
        "Bilgileri Değiştir",
        "Şifre Değiştir"
    )

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }
}
