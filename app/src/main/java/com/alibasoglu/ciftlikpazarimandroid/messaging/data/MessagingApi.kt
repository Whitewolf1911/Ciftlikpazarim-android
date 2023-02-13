package com.alibasoglu.ciftlikpazarimandroid.messaging.data

import com.alibasoglu.ciftlikpazarimandroid.messaging.data.model.MessagesPreviewsRequest
import com.alibasoglu.ciftlikpazarimandroid.messaging.data.model.MessagesRequest
import com.alibasoglu.ciftlikpazarimandroid.messaging.data.model.SendMessageRequest
import com.alibasoglu.ciftlikpazarimandroid.messaging.model.Message
import com.alibasoglu.ciftlikpazarimandroid.messaging.model.MessagePreview
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MessagingApi {

    @POST("/api/getMessagesPreviews")
    suspend fun getMessagesPreviews(
        @Body messagesPreviewsRequest: MessagesPreviewsRequest
    ): Response<List<MessagePreview>>

    @POST("/api/getMessages")
    suspend fun getMessages(
        @Body messagesRequest: MessagesRequest
    ): Response<List<Message>>

    //This returns contacts of the user after message send successfully
    @POST("/api/sendMessage")
    suspend fun sendMessage(
        @Body sendMessageRequest: SendMessageRequest
    ): Response<List<String>>

}
