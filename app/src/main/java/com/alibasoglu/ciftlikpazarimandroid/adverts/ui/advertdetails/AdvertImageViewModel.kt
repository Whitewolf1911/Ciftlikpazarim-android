package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.advertdetails

import androidx.lifecycle.SavedStateHandle
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import com.alibasoglu.ciftlikpazarimandroid.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdvertImageViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : BaseViewModel() {
    val advertImageData = savedStateHandle.getOrThrow<String>(IMAGE_DATA_KEY)

    companion object {
        const val IMAGE_DATA_KEY = "advert_image_data"
    }
}
