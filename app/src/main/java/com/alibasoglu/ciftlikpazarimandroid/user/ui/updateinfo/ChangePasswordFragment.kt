package com.alibasoglu.ciftlikpazarimandroid.user.ui.updateinfo

import android.os.Bundle
import android.view.View
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentChangePasswordBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding

class ChangePasswordFragment : BaseFragment(R.layout.fragment_change_password) {
    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentChangePasswordBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI(){
        with(binding){
            submitButton.setOnClickListener {
                //TODO show dialog to approve
            }
        }
    }
}
