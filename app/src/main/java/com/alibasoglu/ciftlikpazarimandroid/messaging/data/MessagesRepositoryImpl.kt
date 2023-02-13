package com.alibasoglu.ciftlikpazarimandroid.messaging.data

import com.alibasoglu.ciftlikpazarimandroid.core.UserObject
import com.alibasoglu.ciftlikpazarimandroid.messaging.data.model.MessagesPreviewsRequest
import com.alibasoglu.ciftlikpazarimandroid.messaging.data.model.MessagesRequest
import com.alibasoglu.ciftlikpazarimandroid.messaging.data.model.SendMessageRequest
import com.alibasoglu.ciftlikpazarimandroid.messaging.domain.MessagesRepository
import com.alibasoglu.ciftlikpazarimandroid.messaging.model.Message
import com.alibasoglu.ciftlikpazarimandroid.messaging.model.MessagePreview
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class MessagesRepositoryImpl(
    private val messagingApi: MessagingApi,
) : MessagesRepository {

    override suspend fun getMessagesPreviews(): Flow<Resource<List<MessagePreview>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val messagesPreviewsRequest = MessagesPreviewsRequest(uid = UserObject.id, idArray = UserObject.contacts)
            val response = try {
                messagingApi.getMessagesPreviews(messagesPreviewsRequest)
            } catch (e: HttpException) {
                emit(Resource.Error(message = "Bir hata oluştu"))
                null
            } catch (e: IOException) {
                emit(Resource.Error(message = "Bir hata oluştu. İnternet bağlantınızı kontrol edin."))
                null
            }
            response?.let {
                emit(Resource.Success(response.body()))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    override suspend fun getMessages(otherUserId: String): Flow<Resource<List<Message>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val messagesRequest = MessagesRequest(uid = UserObject.id, otherUser = otherUserId)
            val response = try {
                messagingApi.getMessages(messagesRequest = messagesRequest)
            } catch (e: HttpException) {
                emit(Resource.Error(message = "Bir hata oluştu"))
                null
            } catch (e: IOException) {
                emit(Resource.Error(message = "Bir hata oluştu. İnternet bağlantınızı kontrol edin."))
                null
            }
            response?.let {
                emit(Resource.Success(response.body()))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    override suspend fun sendMessage(otherUserId: String, message: String): Flow<Resource<List<String>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val sendMessageRequest = SendMessageRequest(from = UserObject.id, to = otherUserId, message = message)
            val response = try {
                messagingApi.sendMessage(sendMessageRequest = sendMessageRequest)
            } catch (e: HttpException) {
                emit(Resource.Error(message = "Bir hata oluştu"))
                null
            } catch (e: IOException) {
                emit(Resource.Error(message = "Bir hata oluştu. İnternet bağlantınızı kontrol edin."))
                null
            }
            response?.let {
                emit(Resource.Success(response.body()))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

}
