package com.alibasoglu.ciftlikpazarimandroid.messaging.domain

import com.alibasoglu.ciftlikpazarimandroid.messaging.model.Message
import com.alibasoglu.ciftlikpazarimandroid.messaging.model.MessagePreview
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {

    suspend fun getMessagesPreviews(): Flow<Resource<List<MessagePreview>>>

    suspend fun getMessages(otherUserId: String): Flow<Resource<List<Message>>>

    suspend fun sendMessage(otherUserId: String, message: String): Flow<Resource<List<String>>>
}
