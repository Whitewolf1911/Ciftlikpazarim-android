package com.alibasoglu.ciftlikpazarimandroid.adverts.data

import android.content.SharedPreferences
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.AdvertsApi
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
}
