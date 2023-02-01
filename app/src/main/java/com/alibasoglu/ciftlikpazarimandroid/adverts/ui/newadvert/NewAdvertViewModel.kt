package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.newadvert

import androidx.lifecycle.viewModelScope
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.AdvertsRepository
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NewAdvertViewModel @Inject constructor(
    private val advertsRepository: AdvertsRepository
) : BaseViewModel() {

    private var _newAdvertStateFlow = MutableStateFlow<Resource<Unit>>(Resource.Loading(isLoading = false))
    val newAdvertsStateFlow: StateFlow<Resource<Unit>>
        get() = _newAdvertStateFlow

    suspend fun addNewAdvert(
        name: String,
        description: String,
        userId: String,
        images: List<String>,
        price: Int,
        category: String,
        city: String
    ) {
        viewModelScope.launch {
            val result = advertsRepository.addNewAdvert(
                name = name,
                description = description,
                userId = userId,
                images = images,
                price = price,
                category = category,
                city = city
            )

            when (result) {
                is Resource.Success -> {
                    _newAdvertStateFlow.value = Resource.Success(data = null)
                }
                is Resource.Error -> {
                    _newAdvertStateFlow.value = Resource.Error(message = result.message ?: "error")
                }
                else -> {
                    _newAdvertStateFlow.value = Resource.Loading(isLoading = true)
                }
            }
        }
    }

}
