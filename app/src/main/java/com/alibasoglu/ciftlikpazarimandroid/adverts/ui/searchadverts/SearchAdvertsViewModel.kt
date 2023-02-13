package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.searchadverts

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
class SearchAdvertsViewModel @Inject constructor(
    private val advertsRepository: AdvertsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val searchQuery = savedStateHandle.getOrThrow<String>(SEARCH_QUERY_KEY)

    private var _searchAdvertsFlow = MutableStateFlow<PagingData<Advert>>(PagingData.empty())
    val searchAdvertsFlow: StateFlow<PagingData<Advert>>
        get() = _searchAdvertsFlow


    init {
        getSearchedAdverts(searchQuery)
    }

    fun getSearchedAdverts(searchQuery: String) {
        viewModelScope.launch {
            advertsRepository.getAdvertsPager(category = null, searchQuery = searchQuery)
                .flow
                .cachedIn(viewModelScope)
                .collectLatest {
                    _searchAdvertsFlow.value = it
                }
        }
    }

    fun getInitialSearchQuery(): String {
        return searchQuery
    }

    companion object {
        const val SEARCH_QUERY_KEY = "searchQuery"
    }

}
