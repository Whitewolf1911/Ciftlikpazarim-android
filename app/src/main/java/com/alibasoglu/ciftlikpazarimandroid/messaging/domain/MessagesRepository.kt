package com.alibasoglu.ciftlikpazarimandroid.messaging.domain

import com.alibasoglu.ciftlikpazarimandroid.messaging.model.MessagePreview
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource

interface MessagesRepository {

    suspend fun getMessagesPreviews(): Resource<List<MessagePreview>>

}
