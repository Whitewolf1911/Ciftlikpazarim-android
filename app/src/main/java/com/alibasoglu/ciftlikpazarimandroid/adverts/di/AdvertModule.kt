package com.alibasoglu.ciftlikpazarimandroid.adverts.di

import com.alibasoglu.ciftlikpazarimandroid.adverts.data.AdvertsApi
import com.alibasoglu.ciftlikpazarimandroid.adverts.data.AdvertsRepositoryImpl
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.AdvertsRepository
import com.alibasoglu.ciftlikpazarimandroid.di.AppModule
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
object AdvertModule {

    @Provides
    @Singleton
    fun provideAdvertsApi(okHttpClient: OkHttpClient): AdvertsApi {
        return Retrofit.Builder()
            .baseUrl(AppModule.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideAdvertsRepository(api: AdvertsApi): AdvertsRepository {
        return AdvertsRepositoryImpl(api)
    }
}
