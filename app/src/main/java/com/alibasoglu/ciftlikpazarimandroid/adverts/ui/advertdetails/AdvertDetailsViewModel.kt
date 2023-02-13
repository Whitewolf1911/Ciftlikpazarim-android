package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.advertdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alibasoglu.ciftlikpazarimandroid.User
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.AdvertsRepository
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import com.alibasoglu.ciftlikpazarimandroid.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class AdvertDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val advertsRepository: AdvertsRepository
) : BaseViewModel() {

    val advert = savedStateHandle.getOrThrow<Advert>("advert")

    private var _advertOwnerState = MutableStateFlow(
        User(
            _id = null,
            name = "",
            email = "",
            phoneNumber = "",
            type = "",
            token = "",
            deviceToken = "",
            favorites = listOf(),
            contacts = listOf(),
            blockedUsers = listOf()
        )
    )
    val advertOwnerState: StateFlow<User>
        get() = _advertOwnerState

    fun getAdvertDetails(): Advert {
        return advert
    }

    init {
        getAdvertOwner()
    }

    private fun getAdvertOwner() {
        viewModelScope.launch {
            advertsRepository.getUserById(advert.userID).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { user ->
                            _advertOwnerState.value = user
                            Log.d("mylog", user.phoneNumber + user.name)
                        }
                    }
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }

}
