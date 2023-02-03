package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.searchadverts

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.AdvertsRepository
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import com.alibasoglu.ciftlikpazarimandroid.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class SearchAdvertsViewModel @Inject constructor(
    private val advertsRepository: AdvertsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val searchQuery = savedStateHandle.getOrThrow<String>(SEARCH_QUERY_KEY)

    private val _searchAdvertsFlow = MutableStateFlow<PagingData<Advert>>(PagingData.empty())
    val searchAdvertsFlow: StateFlow<PagingData<Advert>>
        get() = _searchAdvertsFlow


    init {

    }

    companion object {
        const val SEARCH_QUERY_KEY = "searchQuery"
    }

}
