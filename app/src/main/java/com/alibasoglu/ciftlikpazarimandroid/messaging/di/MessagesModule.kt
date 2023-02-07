package com.alibasoglu.ciftlikpazarimandroid.messaging.di

import com.alibasoglu.ciftlikpazarimandroid.di.AppModule
import com.alibasoglu.ciftlikpazarimandroid.messaging.data.MessagesRepositoryImpl
import com.alibasoglu.ciftlikpazarimandroid.messaging.data.MessagingApi
import com.alibasoglu.ciftlikpazarimandroid.messaging.domain.MessagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


@Module
@InstallIn(SingletonComponent::class)
object MessagesModule {

    @Provides
    @Singleton
    fun provideMessagingApi(okHttpClient: OkHttpClient): MessagingApi {
        return Retrofit.Builder()
            .baseUrl(AppModule.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideMessagesRepository(messagingApi: MessagingApi): MessagesRepository {
        return MessagesRepositoryImpl(messagingApi)
    }
}
