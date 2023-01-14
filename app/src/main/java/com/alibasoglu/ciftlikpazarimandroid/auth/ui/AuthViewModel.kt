package com.alibasoglu.ciftlikpazarimandroid.auth.ui

import androidx.lifecycle.viewModelScope
import com.alibasoglu.ciftlikpazarimandroid.auth.AuthRepository
import com.alibasoglu.ciftlikpazarimandroid.auth.AuthResult
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _authStateFlow = MutableStateFlow<AuthResult<Unit>>(AuthResult.WaitingRequest())
    val authStateFlow: StateFlow<AuthResult<Unit>>
        get() = _authStateFlow

    private val _signUpStateFlow = MutableStateFlow<AuthResult<Unit>>(AuthResult.WaitingRequest())
    val signUpStateFlow: StateFlow<AuthResult<Unit>>
        get() = _signUpStateFlow

    init {
        viewModelScope.launch {
            authenticate()
        }
    }

    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        deviceToken: String,
        phoneNumber: String
    ) {
        viewModelScope.launch {
            val result = authRepository.signUp(
                email = email,
                password = password,
                name = name,
                phoneNumber = phoneNumber,
                deviceToken = deviceToken
            )
            when (result) {
                is AuthResult.Authorized -> {
                    _signUpStateFlow.value = AuthResult.Authorized()
                }
                is AuthResult.Unauthorized -> {
                    _signUpStateFlow.value = AuthResult.Unauthorized()
                }
                is AuthResult.UnknownError -> {
                    _signUpStateFlow.value = AuthResult.UnknownError()
                }
                else -> {
                    _authStateFlow.value = AuthResult.Loading()
                }
            }
        }
    }

    suspend fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.signIn(email = email, password = password)
            when (result) {
                is AuthResult.Authorized -> {
                    _authStateFlow.value = AuthResult.Authorized()
                }
                is AuthResult.Unauthorized -> {
                    _authStateFlow.value = AuthResult.Unauthorized()
                }
                is AuthResult.UnknownError -> {
                    _authStateFlow.value = AuthResult.UnknownError()
                }
                else -> {
                    _authStateFlow.value = AuthResult.Loading()
                }
            }
        }
    }

    private suspend fun authenticate() {
        val result = authRepository.authenticate()
        if (result is AuthResult.Authorized) {
            _authStateFlow.value = AuthResult.Authorized()
        }
    }

}
