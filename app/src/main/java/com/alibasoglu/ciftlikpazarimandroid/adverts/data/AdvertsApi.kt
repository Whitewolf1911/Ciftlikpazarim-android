package com.alibasoglu.ciftlikpazarimandroid.adverts.data

import com.alibasoglu.ciftlikpazarimandroid.User
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AdvertsApi {

    @POST("user/add-advert")
    suspend fun addNewAdvert(
        @Body advert: Advert
    ): Response<ResponseBody>

    @GET("api/adverts")
    suspend fun getCategoryAdverts(
        @Query("category") category: String, @Query("page") page: Int
    ): Response<List<Advert>>

    @GET("/api/adverts/search")
    suspend fun getSearchQueryAdverts(
        @Query("search") searchQuery: String, @Query("page") page: Int
    ): Response<List<Advert>>

    //TODO (Refactor) move this to UsersApi
    @GET("get-user-by-id")
    suspend fun getUserById(
        @Query("userID") userId: String
    ): Response<User>

}
