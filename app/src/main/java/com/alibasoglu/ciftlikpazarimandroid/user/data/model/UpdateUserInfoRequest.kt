package com.alibasoglu.ciftlikpazarimandroid.user.data.model

data class UpdateUserInfoRequest(
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String
)
