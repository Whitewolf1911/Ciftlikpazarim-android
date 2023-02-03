package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.categoryadverts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.AdvertsRepository
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import com.alibasoglu.ciftlikpazarimandroid.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryAdvertsViewModel @Inject constructor(
    private val advertsRepository: AdvertsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val category = savedStateHandle.getOrThrow<String>(CATEGORY_KEY)

    private val _categoryAdvertsState = MutableStateFlow<PagingData<Advert>>(PagingData.empty())
    val categoryAdvertsState: StateFlow<PagingData<Advert>>
        get() = _categoryAdvertsState

    init {
        getCategoryAdverts()
    }

    fun getCategoryName(): String {
        return category
    }

    private fun getCategoryAdverts() {
        viewModelScope.launch {
            advertsRepository.getAdvertsPager(category = category, searchQuery = null)
                .flow
                .cachedIn(viewModelScope).collectLatest {
                    _categoryAdvertsState.value = it
                }
        }
    }

    companion object {
        const val CATEGORY_KEY = "category"
    }
}
