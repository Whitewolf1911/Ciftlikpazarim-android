package com.alibasoglu.ciftlikpazarimandroid.auth.data

import android.content.SharedPreferences
import android.util.Log
import com.alibasoglu.ciftlikpazarimandroid.User
import com.alibasoglu.ciftlikpazarimandroid.auth.AuthResult
import com.alibasoglu.ciftlikpazarimandroid.auth.SigninRequest
import com.alibasoglu.ciftlikpazarimandroid.auth.SignupRequest
import com.alibasoglu.ciftlikpazarimandroid.auth.domain.AuthRepository
import com.alibasoglu.ciftlikpazarimandroid.auth.ui.TokenCheckRequest
import com.alibasoglu.ciftlikpazarimandroid.core.UserObject
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
        phoneNumber: String
    ): AuthResult<Unit> {
        return try {
            val deviceToken = sharedPreferences.getString("deviceToken", "") ?: ""
            val result = api.signUp(
                request = SignupRequest(
                    email = email,
                    password = password,
                    name = name,
                    phoneNumber = phoneNumber,
                    deviceToken = deviceToken
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
                checkUpdateDeviceToken()
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
                    checkUpdateDeviceToken()
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

    override suspend fun checkUpdateDeviceToken() {
        try {
            val deviceToken = sharedPreferences.getString("deviceToken", "") ?: ""
            val authToken = sharedPreferences.getString(AUTH_TOKEN_PREFERENCE_NAME, "") ?: ""

            val request = TokenCheckRequest(id = UserObject.id, token = deviceToken)
            api.checkUpdateDeviceToken(request = request, authToken = authToken)
        } catch (e: Exception) {
            Log.d("exception", " Exception occurred during token check/update request")
        }
    }

    companion object {
        const val AUTH_TOKEN_PREFERENCE_NAME = "authToken"
    }
}
