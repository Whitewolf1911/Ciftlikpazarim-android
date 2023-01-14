package com.alibasoglu.ciftlikpazarimandroid.auth

import android.content.SharedPreferences
import android.util.Log
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
                )
            )
            if (result.isSuccessful) {
                AuthResult.Authorized()
            } else {
                AuthResult.Unauthorized()
            }
        } catch (e: HttpException) {
            if (e.code() == 400) {
                Log.d("httpErrorHandle", "Http exception error code 400 occurred")
            } else {
                Log.d("httpErrorHandle", "Http exception error code something else occurred")
            }
            AuthResult.Unauthorized()
        } catch (e: Exception) {
            Log.d("errorHandle", e.toString())
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
            sharedPreferences.edit()
                .putString("authToken", response.body()?.token)
                .apply()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 400) {
                Log.d("httpErrorHandle", "Http exception error code 400 occurred")
            } else {
                Log.d("httpErrorHandle", "Http exception error code something else occurred")
            }
            AuthResult.Unauthorized()
        } catch (e: Exception) {
            Log.d("errorHandle", "Exception has occurred")
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = sharedPreferences.getString("authToken", null) ?: return AuthResult.Unauthorized()
            val response = api.authenticate(token)
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 400) {
                Log.d("httpErrorHandle", "Http exception error code 400 occurred")
            } else {
                Log.d("httpErrorHandle", "Http exception error code something else occurred")
            }
            AuthResult.Unauthorized()
        } catch (e: Exception) {
            Log.d("errorHandle", "Exception has occurred")
            AuthResult.UnknownError()
        }
    }
}
