package com.alibasoglu.ciftlikpazarimandroid

import android.content.SharedPreferences
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    fun saveDeviceTokenToSharedPref(deviceToken: String) {
        sharedPreferences.edit().putString("deviceToken", deviceToken).apply()
    }

}
