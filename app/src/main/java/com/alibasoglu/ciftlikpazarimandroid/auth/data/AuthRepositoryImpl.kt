package com.alibasoglu.ciftlikpazarimandroid.auth.data

import android.content.SharedPreferences
import com.alibasoglu.ciftlikpazarimandroid.User
import com.alibasoglu.ciftlikpazarimandroid.auth.AuthResult
import com.alibasoglu.ciftlikpazarimandroid.auth.SigninRequest
import com.alibasoglu.ciftlikpazarimandroid.auth.SignupRequest
import com.alibasoglu.ciftlikpazarimandroid.auth.domain.AuthRepository
import com.alibasoglu.ciftlikpazarimandroid.core.setUserObjectFromModel
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    override suspend fun signUp(
        email: String,
        password: String,
        name: String,
        phoneNumber: String,
        deviceToken: String
    ): AuthResult<Unit> {
        return try {
            val result = api.signUp(
                request = SignupRequest(
                    email = email,
                    password = password,
                    name = name,
                    phoneNumber = phoneNumber,
                    deviceToken = "device_token-123"
                    // TODO device token will be implemented after firebase
                )
            )
            if (result.isSuccessful) {
                AuthResult.Authorized()
            } else {
                AuthResult.Unauthorized()
            }
        } catch (e: HttpException) {
            AuthResult.Unauthorized()
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signIn(
                request = SigninRequest(
                    email = email,
                    password = password
                )
            )
            if (response.isSuccessful) {
                sharedPreferences.edit()
                    .putString(AUTH_TOKEN_PREFERENCE_NAME, response.body()?.token)
                    .apply()
                val user = response.body()
                if (user != null) {
                    setUserObjectFromModel(user)
                }
                AuthResult.Authorized()
            } else {
                AuthResult.Unauthorized()
            }
        } catch (e: HttpException) {
            AuthResult.Unauthorized()
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token =
                sharedPreferences.getString(AUTH_TOKEN_PREFERENCE_NAME, null) ?: return AuthResult.Unauthorized()
            val response = api.authenticate(token)
            if (response.isSuccessful && response.body() == true) {
                val userData = getUserInfo()
                userData?.let { user ->
                    setUserObjectFromModel(user)
                    return AuthResult.Authorized()
                }
                AuthResult.Unauthorized()
            } else {
                AuthResult.Unauthorized()
            }
        } catch (e: HttpException) {
            AuthResult.Unauthorized()
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun getUserInfo(): User? {
        return try {
            val token =
                sharedPreferences.getString(AUTH_TOKEN_PREFERENCE_NAME, null) ?: ""
            val response = api.getUserData(authToken = token)
            if (response.isSuccessful && response.body() != null) {
                response.body()
            } else {
                null
            }
        } catch (e: HttpException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        const val AUTH_TOKEN_PREFERENCE_NAME = "authToken"
    }
}
