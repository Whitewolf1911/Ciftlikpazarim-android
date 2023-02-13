package com.alibasoglu.ciftlikpazarimandroid.adverts.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alibasoglu.ciftlikpazarimandroid.User
import com.alibasoglu.ciftlikpazarimandroid.adverts.data.pagingsource.AdvertsPagingSource
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.AdvertsRepository
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class AdvertsRepositoryImpl(
    private val api: AdvertsApi,
) : AdvertsRepository {

    override suspend fun addNewAdvert(
        name: String,
        description: String,
        userId: String,
        images: List<String>,
        price: Int,
        category: String,
        city: String
    ): Resource<String> {

        return try {
            val advert = Advert(
                name = name,
                description = description,
                userID = userId,
                images = images,
                price = price,
                category = category,
                city = city
            )

            val response = api.addNewAdvert(advert = advert)
            if (response.isSuccessful) {
                return Resource.Success(data = null)
            } else {
                return Resource.Error(message = "Hata oluştu.")
            }

        } catch (e: Exception) {
            Resource.Error(e.toString())
        }
    }

    override fun getAdvertsPager(category: String?, searchQuery: String?) = Pager(
        config = PagingConfig(
            pageSize = AdvertsPagingSource.ADVERTS_PAGE_SIZE,
            initialLoadSize = AdvertsPagingSource.ADVERTS_PAGE_SIZE,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = { AdvertsPagingSource(advertsApi = api, category = category, searchQuery = searchQuery) }
    )

    override suspend fun getUserById(userId: String): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val response = try {
                api.getUserById(userId = userId)
            } catch (e: HttpException) {
                Log.d("mylog", e.toString())
                emit(Resource.Error(message = "Bir hata oluştu"))
                null
            } catch (e: IOException) {
                Log.d("mylog", e.toString())
                emit(Resource.Error(message = "Bir hata oluştu. İnternet bağlantınızı kontrol edin."))
                null
            }
            response?.let {
                Log.d("mylog", response.toString())

                emit(Resource.Success(data = response.body()))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }
}
