package com.alibasoglu.ciftlikpazarimandroid.messaging.domain

import com.alibasoglu.ciftlikpazarimandroid.messaging.model.MessagePreview
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {

    suspend fun getMessagesPreviews(): Flow<Resource<List<MessagePreview>>>
}
