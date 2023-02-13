package com.alibasoglu.ciftlikpazarimandroid.messaging.ui.messagespreviews

import com.alibasoglu.ciftlikpazarimandroid.messaging.model.MessagePreview

data class MessagesPreviewState(
    var isLoading: Boolean = false,
    var messagesPreviews: List<MessagePreview> = emptyList(),
    val error: String? = null
)
