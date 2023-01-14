package com.alibasoglu.ciftlikpazarimandroid.auth

data class SignupRequest(
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val deviceToken: String
)
