package com.alibasoglu.ciftlikpazarimandroid.adverts.domain

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AdvertsApi {

    @POST("user/add-advert")
    suspend fun addNewAdvert(
        @Body advert: Advert, @Header("x-auth-token") authToken: String
    ): Response<ResponseBody>

}
