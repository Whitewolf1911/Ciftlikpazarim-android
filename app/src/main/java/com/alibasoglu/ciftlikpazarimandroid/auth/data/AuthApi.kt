package com.alibasoglu.ciftlikpazarimandroid.auth.data

import com.alibasoglu.ciftlikpazarimandroid.User
import com.alibasoglu.ciftlikpazarimandroid.auth.SigninRequest
import com.alibasoglu.ciftlikpazarimandroid.auth.SignupRequest
import com.alibasoglu.ciftlikpazarimandroid.auth.ui.TokenCheckRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("api/signup")
    suspend fun signUp(
        @Body request: SignupRequest
    ): Response<User>

    @POST("api/signin")
    suspend fun signIn(
        @Body request: SigninRequest
    ): Response<User>

    @POST("tokenIsValid")
    suspend fun authenticate(
        @Header("x-auth-token") authToken: String
    ): Response<Boolean>

    @GET("/")
    suspend fun getUserData(
        @Header("x-auth-token") authToken: String
    ): Response<User>

    @POST("check-device-token")
    suspend fun checkUpdateDeviceToken(
        @Header("x-auth-token") authToken: String,
        @Body request: TokenCheckRequest
    ): Response<String>

}
