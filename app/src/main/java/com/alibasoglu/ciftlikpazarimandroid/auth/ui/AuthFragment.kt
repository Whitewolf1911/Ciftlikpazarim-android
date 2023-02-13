package com.alibasoglu.ciftlikpazarimandroid.auth.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.auth.AuthResult
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentAuthBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.lifecycle.observe
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : BaseFragment(R.layout.fragment_auth) {

    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentAuthBinding::bind)

    private val authViewModel by viewModels<AuthViewModel>()

    private val authStateCollector = FlowCollector<AuthResult<Unit>> { value ->
        processAuthState(value)
    }

    private val signUpStateCollector = FlowCollector<AuthResult<Unit>> { value ->
        processSignUpState(value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
    }

    private fun initUi() {
        // Bottom Bar shouldn't be visible in auth screen
        hideBottomNavBar()

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

    private fun processSignUpState(value: AuthResult<Unit>) {
        when (value) {
            is AuthResult.Authorized -> {
                Toast.makeText(context, "Hesap oluşturuldu!", Toast.LENGTH_SHORT).show()
            }
            is AuthResult.Unauthorized -> {
                Toast.makeText(context, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
            }
            is AuthResult.UnknownError -> {
                Toast.makeText(context, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun processAuthState(value: AuthResult<Unit>) {
        when (value) {
            is AuthResult.Authorized -> {
                nav(AuthFragmentDirections.actionAuthFragmentToHomeFragment())
            }
            is AuthResult.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is AuthResult.Unauthorized -> {
                Toast.makeText(context, getString(R.string.wrong_uname_pass), Toast.LENGTH_SHORT).show()
            }
            is AuthResult.UnknownError -> {
                Toast.makeText(context, R.string.error_occurred, Toast.LENGTH_SHORT).show()
            }
            is AuthResult.WaitingRequest -> {
                // Do nothing wait the users login request
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.observe {
            authViewModel.authStateFlow.collect(authStateCollector)
        }
        viewLifecycleOwner.observe {
            authViewModel.signUpStateFlow.collect(signUpStateCollector)
        }
    }
}
