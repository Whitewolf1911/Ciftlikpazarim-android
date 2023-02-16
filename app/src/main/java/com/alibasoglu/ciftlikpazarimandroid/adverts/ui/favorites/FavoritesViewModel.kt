package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.favorites

import androidx.lifecycle.viewModelScope
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.AdvertsRepository
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val advertsRepository: AdvertsRepository
) : BaseViewModel() {

    private val _favoriteAdvertsState = MutableStateFlow<Resource<List<Advert>>>(Resource.Loading(isLoading = true))
    val favoriteAdvertsState: StateFlow<Resource<List<Advert>>>
        get() = _favoriteAdvertsState

    fun getFavoriteAdverts() {
        viewModelScope.launch {
            advertsRepository.getFavoriteAdverts().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { advertList ->
                            _favoriteAdvertsState.value = Resource.Success(data = advertList)
                        }
                    }
                    is Resource.Error -> {
                        _favoriteAdvertsState.value = Resource.Error(result.message ?: "error")
                    }
                    is Resource.Loading -> {
                        _favoriteAdvertsState.value = Resource.Loading(isLoading = result.isLoading)
                    }
                }
            }
        }
    }
}
