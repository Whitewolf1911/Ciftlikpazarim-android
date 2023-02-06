package com.alibasoglu.ciftlikpazarimandroid.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.alibasoglu.ciftlikpazarimandroid.R

fun NavController.navigateSafe(directions: NavDirections, onError: (() -> Unit)? = null) {
    try {
        navigate(directions, navOptions)
    } catch (exception: IllegalArgumentException) {
        onError?.invoke()
    }
}

private val navOptions = NavOptions.Builder().apply {
    setEnterAnim(R.anim.slide_in)
    setExitAnim(R.anim.fade_out)
    setPopEnterAnim(R.anim.fade_in)
    setPopExitAnim(R.anim.slide_out)
}.build()
