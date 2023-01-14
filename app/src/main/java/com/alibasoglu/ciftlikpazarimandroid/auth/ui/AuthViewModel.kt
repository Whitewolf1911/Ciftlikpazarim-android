package com.alibasoglu.ciftlikpazarimandroid.auth.ui

import androidx.lifecycle.viewModelScope
import com.alibasoglu.ciftlikpazarimandroid.auth.AuthRepository
import com.alibasoglu.ciftlikpazarimandroid.auth.AuthResult
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    //private val _signupResultState =

    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        deviceToken: String,
        phoneNumber: String
    ): Boolean {
        var resultBoolean = false
        viewModelScope.launch {
            val result = authRepository.signUp(
                email = email,
                password = password,
                name = name,
                phoneNumber = phoneNumber,
                deviceToken = deviceToken
            )
            resultBoolean = when (result) {
                is AuthResult.Authorized -> {
                    true
                }
                is AuthResult.Unauthorized -> {
                    false
                }
                is AuthResult.UnknownError -> {
                    false
                }

            }
        }
        return resultBoolean
    }

    suspend fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signIn(email = email, password = password)
        }
    }

}
