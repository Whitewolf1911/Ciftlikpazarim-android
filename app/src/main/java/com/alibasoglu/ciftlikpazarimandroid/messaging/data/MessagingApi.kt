package com.alibasoglu.ciftlikpazarimandroid.messaging.data

import com.alibasoglu.ciftlikpazarimandroid.messaging.data.model.MessagesPreviewsRequest
import com.alibasoglu.ciftlikpazarimandroid.messaging.model.MessagePreview
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MessagingApi {

    @POST("/api/getMessagesPreviews")
    suspend fun getMessagesPreviews(
        @Body messagesPreviewsRequest: MessagesPreviewsRequest
    ): Response<List<MessagePreview>>

}
