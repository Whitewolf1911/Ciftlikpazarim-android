package com.alibasoglu.ciftlikpazarimandroid.adverts.domain

data class Advert(
    val _id: String? = null,
    val name: String,
    val description: String,
    val userID: String,
    val images: List<String>,
    val price: Int,
    val category: String,
    val city: String,
    val isPublished: Boolean? = null
)
