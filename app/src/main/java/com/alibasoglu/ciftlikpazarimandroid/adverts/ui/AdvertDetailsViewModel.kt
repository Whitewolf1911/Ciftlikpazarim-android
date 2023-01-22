package com.alibasoglu.ciftlikpazarimandroid.adverts.ui

import androidx.lifecycle.SavedStateHandle
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import com.alibasoglu.ciftlikpazarimandroid.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdvertDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val advert = savedStateHandle.getOrThrow<Advert>("advert")

    fun getAdvertDetails(): Advert {
        return advert
    }

}
