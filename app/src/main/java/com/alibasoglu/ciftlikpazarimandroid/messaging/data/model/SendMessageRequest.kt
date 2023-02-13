package com.alibasoglu.ciftlikpazarimandroid.messaging.data.model

data class SendMessageRequest(
    val from: String,
    val to: String,
    val message: String
)
