package com.alibasoglu.ciftlikpazarimandroid.user.domain

import com.alibasoglu.ciftlikpazarimandroid.ResponseSuccessMessage
import com.alibasoglu.ciftlikpazarimandroid.User
import com.alibasoglu.ciftlikpazarimandroid.user.data.model.ChangePasswordRequest
import com.alibasoglu.ciftlikpazarimandroid.user.data.model.UpdateUserInfoRequest
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun updateUserInfo(changeUserInfoRequest: UpdateUserInfoRequest): Flow<Resource<User>>

    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): Flow<Resource<ResponseSuccessMessage>>
}
