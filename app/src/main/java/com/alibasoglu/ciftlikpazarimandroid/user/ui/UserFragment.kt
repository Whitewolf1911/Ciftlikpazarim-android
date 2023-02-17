package com.alibasoglu.ciftlikpazarimandroid.user.ui

import android.os.Bundle
import android.view.View
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.UserObject
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentUserBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding

class UserFragment() : BaseFragment(R.layout.fragment_user) {

    val toolbarConfiguration = ToolbarConfiguration(titleResId = R.string.my_account)
    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration)

    private val binding by viewBinding(FragmentUserBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        with(binding) {
            with(UserObject) {
                nameTextView.text = name
                emailTextView.text = email
                phoneTextView.text = phoneNumber
            }

            myAdvertsButton.setOnClickListener {
                //TODO nav to myAdverts screen
            }
            updateMyInfoButton.setOnClickListener {
                //TODO nav to Update my info screen
            }
            logoutButton.setOnClickListener {
                //TODO logout
            }
        }
    }

}
