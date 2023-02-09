package com.alibasoglu.ciftlikpazarimandroid.messaging.ui.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import com.alibasoglu.ciftlikpazarimandroid.messaging.domain.MessagesRepository
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import com.alibasoglu.ciftlikpazarimandroid.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messagesRepository: MessagesRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val otherUserId = savedStateHandle.getOrThrow<String>(OTHER_USER_ID_KEY)

    fun getOtherUserId() = otherUserId

    private var _state by mutableStateOf(ChatState())
    val state: ChatState
        get() = _state


    init {
        getMessages(otherUserId)
    }

    fun getMessages(otherUserId: String) {
        viewModelScope.launch {
            messagesRepository.getMessages(otherUserId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { messages ->
                            _state = _state.copy(
                                messages = messages,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state = _state.copy(error = result.message, isLoading = false)
                    }
                    is Resource.Loading -> {
                        _state = _state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

    fun sendMessage() {
        //TODO send message
    }

    companion object {
        const val OTHER_USER_ID_KEY = "id"
    }
}
