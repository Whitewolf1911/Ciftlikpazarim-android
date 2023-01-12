package com.alibasoglu.ciftlikpazarimandroid.utils

import androidx.annotation.NonNull
import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.navigateSafe(@NonNull directions: NavDirections, onError: (() -> Unit)? = null) {
    try {
        navigate(directions)
    } catch (exception: IllegalArgumentException) {
        onError?.invoke()
    }
}
