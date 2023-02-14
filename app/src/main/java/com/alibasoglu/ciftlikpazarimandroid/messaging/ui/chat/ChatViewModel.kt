package com.alibasoglu.ciftlikpazarimandroid.messaging.ui.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alibasoglu.ciftlikpazarimandroid.core.BaseViewModel
import com.alibasoglu.ciftlikpazarimandroid.core.UserObject
import com.alibasoglu.ciftlikpazarimandroid.messaging.domain.MessagesRepository
import com.alibasoglu.ciftlikpazarimandroid.messaging.model.Message
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

    var state by mutableStateOf(ChatState())

    init {
        getMessages(otherUserId)
    }

    fun getMessages(otherUserId: String) {
        viewModelScope.launch {
            messagesRepository.getMessages(otherUserId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { messages ->
                            state = state.copy(
                                messages = messages.reversed(),
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        state = state.copy(error = result.message, isLoading = false)
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            messagesRepository
                .sendMessage(message = message, otherUserId = otherUserId).collectLatest { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                UserObject.contacts = result.data
                            }
                            val messagesList = state.messages.toMutableList()
                            messagesList.add(
                                Message(
                                    from = UserObject.id,
                                    message = message,
                                    to = otherUserId
                                )
                            )
                            val updatedList = messagesList.toList()
                            state = state.copy(messages = updatedList)
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> Unit
                    }
                }
        }
    }

    companion object {
        const val OTHER_USER_ID_KEY = "id"
    }
}
