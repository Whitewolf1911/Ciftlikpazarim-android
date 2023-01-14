package com.alibasoglu.ciftlikpazarimandroid.auth.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentAuthBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : BaseFragment(R.layout.fragment_auth) {

    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentAuthBinding::bind)

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        // Bottom Bar shouldn't be visible in auth screen
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.isVisible = false

        with(binding) {
            signUpButton.setOnClickListener {
                lifecycleScope.launch {
                    authViewModel.signUp(
                        email = signupEmailEditText.text.toString(),
                        name = nameEditText.text.toString(),
                        phoneNumber = phoneNumberEditText.text.toString(),
                        deviceToken = "devicetoken",
                        password = signupPasswordEditText.text.toString()
                    )
                }
            }
            loginButton.setOnClickListener {
                lifecycleScope.launch {
                    authViewModel.signIn(
                        email = emailEditText.text.toString(),
                        password = passwordEditText.text.toString()
                    )
                }
            }
        }
    }

}
