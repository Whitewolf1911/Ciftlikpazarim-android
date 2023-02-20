package com.alibasoglu.ciftlikpazarimandroid.user.ui.updateinfo

import android.os.Bundle
import android.view.View
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.UserObject
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentChangeUserInfoBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding

class ChangeUserInfoFragment : BaseFragment(R.layout.fragment_change_user_info) {
    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentChangeUserInfoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        with(binding) {
            with(UserObject){
                nameEditText.setText(name)
                phoneEditText.setText(phoneNumber)
                emailEditText.setText(email)
            }

            submitButton.setOnClickListener {
                //TODO save the new info
            }
        }
    }
}
