package com.alibasoglu.ciftlikpazarimandroid.adverts.ui

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
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class CategoryAdvertsViewModel @Inject constructor(
    private val advertsRepository: AdvertsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val category = savedStateHandle.getOrThrow<String>("category")

    fun getCategoryName(): String {
        return category
    }

    fun getCategoryAdverts(): Flow<PagingData<Advert>> {
        return advertsRepository.getCategoryAdvertsPager(category = category)
            .flow
            .cachedIn(viewModelScope)
    }
}
