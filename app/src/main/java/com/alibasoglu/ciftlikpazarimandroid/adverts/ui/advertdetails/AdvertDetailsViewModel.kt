package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.advertdetails

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

    private var _addToFavoritesState = MutableStateFlow<Resource<Unit>>(Resource.Loading(isLoading = false))
    val addToFavoritesState: StateFlow<Resource<Unit>>
        get() = _addToFavoritesState

    private var _removeFromFavoritesState = MutableStateFlow<Resource<Unit>>(Resource.Loading(isLoading = false))
    val removeFromFavoritesState: StateFlow<Resource<Unit>>
        get() = _removeFromFavoritesState

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
                        }
                    }
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }

    fun addAdvertToFavorites() {
        viewModelScope.launch {
            advert._id?.let { id ->
                advertsRepository.addAdvertToFavorites(advertId = id).collectLatest { result ->
                    when (result) {
                        is Resource.Success -> {
                            _addToFavoritesState.value = Resource.Success(data = null)
                        }
                        is Resource.Error -> {
                            _addToFavoritesState.value = Resource.Error("Bir hata oluştu. Daha sonra tekrar deneyiniz.")
                        }
                        is Resource.Loading -> {
                            _addToFavoritesState.value = Resource.Loading(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

    fun removeAdvertFromFavorites() {
        viewModelScope.launch {
            advert._id?.let { id ->
                advertsRepository.removeAdvertFromFavorites(advertId = id).collectLatest { result ->
                    when (result) {
                        is Resource.Success -> {
                            _removeFromFavoritesState.value = Resource.Success(data = null)
                        }
                        is Resource.Error -> {
                            _removeFromFavoritesState.value = Resource.Error("Bir hata oluştu. Daha sonra tekrar deneyiniz.")
                        }
                        is Resource.Loading -> {
                            _removeFromFavoritesState.value = Resource.Loading(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }
}
