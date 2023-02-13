package com.alibasoglu.ciftlikpazarimandroid.messaging.ui.chat

import com.alibasoglu.ciftlikpazarimandroid.messaging.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
