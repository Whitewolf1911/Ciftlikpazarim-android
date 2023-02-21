package com.alibasoglu.ciftlikpazarimandroid.user.data

import com.alibasoglu.ciftlikpazarimandroid.ResponseSuccessMessage
import com.alibasoglu.ciftlikpazarimandroid.User
import com.alibasoglu.ciftlikpazarimandroid.user.data.model.ChangePasswordRequest
import com.alibasoglu.ciftlikpazarimandroid.user.data.model.UpdateUserInfoRequest
import com.alibasoglu.ciftlikpazarimandroid.user.domain.UserRepository
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository {
    override suspend fun updateUserInfo(changeUserInfoRequest: UpdateUserInfoRequest): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val response = try {
                api.updateUserInfo(changeUserInfoRequest)
            } catch (e: HttpException) {
                emit(Resource.Error(message = "Bir hata oluştu"))
                null
            } catch (e: IOException) {
                emit(Resource.Error(message = "Bir hata oluştu. İnternet bağlantınızı kontrol edin."))
                null
            }
            response?.let {
                emit(Resource.Success(it.body()))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): Flow<Resource<ResponseSuccessMessage>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val response = try {
                api.changePassword(changePasswordRequest)
            } catch (e: HttpException) {
                emit(Resource.Error(message = "Bir hata oluştu"))
                null
            } catch (e: IOException) {
                emit(Resource.Error(message = "Bir hata oluştu. İnternet bağlantınızı kontrol edin."))
                null
            }
            response?.let {
                emit(Resource.Success(it.body()))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }
}
