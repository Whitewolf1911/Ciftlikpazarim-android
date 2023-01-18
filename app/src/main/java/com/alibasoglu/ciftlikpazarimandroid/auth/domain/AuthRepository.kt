package com.alibasoglu.ciftlikpazarimandroid.auth.domain

import com.alibasoglu.ciftlikpazarimandroid.auth.AuthResult

interface AuthRepository {

    suspend fun signUp(
        email: String,
        password: String,
        name: String,
        phoneNumber: String,
        deviceToken: String
    ): AuthResult<Unit>

    suspend fun signIn(email: String, password: String): AuthResult<Unit>

    suspend fun authenticate(): AuthResult<Unit>

}