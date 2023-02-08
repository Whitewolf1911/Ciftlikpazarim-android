package com.alibasoglu.ciftlikpazarimandroid.messaging.ui.messagespreviews

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import com.alibasoglu.ciftlikpazarimandroid.messaging.domain.MessagesRepository
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class MessagesPreviewViewModel @Inject constructor(
    private val messagesRepository: MessagesRepository
) : BaseViewModel() {

    private var _state by mutableStateOf(MessagesPreviewState())
    val state : MessagesPreviewState
        get() = _state

    init {
        getMessagesPreview()
    }

    fun getMessagesPreview() {
        viewModelScope.launch {
            messagesRepository.getMessagesPreviews().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { previews ->
                            _state = _state.copy(
                                messagesPreviews = previews
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state = _state.copy(isLoading = false, error = result.message)
                    }
                    is Resource.Loading -> {
                        _state = _state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

}
