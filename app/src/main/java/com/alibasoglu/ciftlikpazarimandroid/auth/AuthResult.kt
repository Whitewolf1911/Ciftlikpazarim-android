package com.alibasoglu.ciftlikpazarimandroid.auth

sealed class AuthResult<T>(val data: T? = null) {
    class Authorized<T>(data: T? = null) : AuthResult<T>(data)
    class Unauthorized<T> : AuthResult<T>()
    class UnknownError<T> : AuthResult<T>()
    class Loading<T> : AuthResult<T>()
    class WaitingRequest<T> : AuthResult<T>()
}
