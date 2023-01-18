package com.alibasoglu.ciftlikpazarimandroid.adverts.data

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alibasoglu.ciftlikpazarimandroid.adverts.data.pagingsource.AdvertsPagingSource
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.AdvertsRepository
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource

class AdvertsRepositoryImpl(
    private val api: AdvertsApi,
    private val sharedPreferences: SharedPreferences,
) : AdvertsRepository {

    override suspend fun addNewAdvert(
        name: String,
        description: String,
        userId: String,
        images: List<String>,
        price: Int,
        category: String,
        city: String
    ): Resource<String> {

        return try {
            val advert = Advert(
                name = name,
                description = description,
                userID = userId,
                images = images,
                price = price,
                category = category,
                city = city
            )
            val authToken = sharedPreferences.getString("authToken", null)

            val response = api.addNewAdvert(
                advert = advert, authToken = authToken.orEmpty()
            )
            if (response.isSuccessful) {
                return Resource.Success(data = null)
            } else {
                return Resource.Error(message = "Hata oluştu.")
            }

        } catch (e: Exception) {
            Resource.Error(e.toString())
        }
    }

    override fun getCategoryAdvertsPager(category: String) = Pager(
        config = PagingConfig(
            pageSize = AdvertsPagingSource.ADVERTS_PAGE_SIZE,
            initialLoadSize = AdvertsPagingSource.ADVERTS_PAGE_SIZE,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = { AdvertsPagingSource(advertsApi = api, category = category) }
    )
}
