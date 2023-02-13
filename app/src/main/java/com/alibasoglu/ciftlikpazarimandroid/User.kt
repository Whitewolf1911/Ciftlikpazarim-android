package com.alibasoglu.ciftlikpazarimandroid

data class User(
    val _id: String?,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val type: String,
    val token: String,
    val deviceToken: String,
    val favorites: List<String>,
    val contacts: List<String>,
    val blockedUsers: List<String>
)
