package com.alibasoglu.ciftlikpazarimandroid.adverts.domain

import com.alibasoglu.ciftlikpazarimandroid.utils.list.RecyclerListItem

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
) : RecyclerListItem {
    override fun areItemsTheSame(other: RecyclerListItem): Boolean {
        return other is Advert && other._id == _id
    }

    override fun areContentsTheSame(other: RecyclerListItem): Boolean {
        return other is Advert && other == this
    }

}
