package com.alibasoglu.ciftlikpazarimandroid.messaging.data

import com.alibasoglu.ciftlikpazarimandroid.UserObject
import com.alibasoglu.ciftlikpazarimandroid.messaging.data.model.MessagesPreviewsRequest
import com.alibasoglu.ciftlikpazarimandroid.messaging.domain.MessagesRepository
import com.alibasoglu.ciftlikpazarimandroid.messaging.model.MessagePreview
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import java.io.IOException
import retrofit2.HttpException

class MessagesRepositoryImpl(
    private val messagingApi: MessagingApi,
) : MessagesRepository {

    override suspend fun getMessagesPreviews(): Resource<List<MessagePreview>> {
        return try {
            val messagesPreviewsRequest = MessagesPreviewsRequest(uid = UserObject.id, idArray = UserObject.contacts)
            val response = messagingApi.getMessagesPreviews(messagesPreviewsRequest)
            Resource.Success(response.body())
        } catch (e: HttpException) {
            Resource.Error(message = "Bir hata oluştu")
        } catch (e: IOException) {
            Resource.Error(message = "Bir hata oluştu. İnternet bağlantınızı kontrol edin.")
        }
    }
}
