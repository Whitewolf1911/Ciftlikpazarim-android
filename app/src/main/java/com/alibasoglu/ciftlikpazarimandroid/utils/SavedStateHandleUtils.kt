package com.alibasoglu.ciftlikpazarimandroid.utils

import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.getOrThrow(argKey: String, exception: Throwable = IllegalArgumentException()): T {
    return get<T>(argKey) ?: throw exception
}

fun <T> SavedStateHandle.getOrElse(argKey: String, defaultValue: T): T {
    return get<T>(argKey) ?: defaultValue
}
