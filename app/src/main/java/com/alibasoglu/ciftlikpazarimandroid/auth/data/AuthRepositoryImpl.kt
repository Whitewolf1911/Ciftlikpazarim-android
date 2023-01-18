package com.alibasoglu.ciftlikpazarimandroid.auth.data

import android.content.SharedPreferences
import com.alibasoglu.ciftlikpazarimandroid.auth.domain.AuthRepository
import com.alibasoglu.ciftlikpazarimandroid.auth.AuthResult
import com.alibasoglu.ciftlikpazarimandroid.auth.SigninRequest
import com.alibasoglu.ciftlikpazarimandroid.auth.SignupRequest
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
                    .putString("authToken", response.body()?.token)
                    .apply()
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
            val token = sharedPreferences.getString("authToken", null) ?: return AuthResult.Unauthorized()
            val response = api.authenticate(token)
            if (response.isSuccessful && response.body() == true) {
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
}
