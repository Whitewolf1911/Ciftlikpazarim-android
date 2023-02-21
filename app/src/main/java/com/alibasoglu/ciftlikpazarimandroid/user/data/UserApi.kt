package com.alibasoglu.ciftlikpazarimandroid.user.data

import com.alibasoglu.ciftlikpazarimandroid.ResponseSuccessMessage
import com.alibasoglu.ciftlikpazarimandroid.User
import com.alibasoglu.ciftlikpazarimandroid.user.data.model.ChangePasswordRequest
import com.alibasoglu.ciftlikpazarimandroid.user.data.model.UpdateUserInfoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("change-user-info")
    suspend fun updateUserInfo(@Body updateUserInfoRequest: UpdateUserInfoRequest): Response<User>

    @POST("change-password")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<ResponseSuccessMessage>

}
