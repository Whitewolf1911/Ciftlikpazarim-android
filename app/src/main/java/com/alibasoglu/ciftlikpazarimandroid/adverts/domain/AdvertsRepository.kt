package com.alibasoglu.ciftlikpazarimandroid.adverts.domain

import androidx.paging.Pager
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource

interface AdvertsRepository {

    suspend fun addNewAdvert(
        name: String,
        description: String,
        userId: String,
        images: List<String>,
        price: Int,
        category: String,
        city: String,
    ): Resource<String>

    fun getAdvertsPager(category: String?, searchQuery: String?): Pager<Int, Advert>

}
