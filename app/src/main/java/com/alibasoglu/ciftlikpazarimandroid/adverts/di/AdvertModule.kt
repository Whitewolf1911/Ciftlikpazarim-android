package com.alibasoglu.ciftlikpazarimandroid.adverts.di

import android.content.SharedPreferences
import com.alibasoglu.ciftlikpazarimandroid.adverts.data.AdvertsRepositoryImpl
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.AdvertsApi
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.AdvertsRepository
import com.alibasoglu.ciftlikpazarimandroid.di.AppModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object AdvertModule {

    @Provides
    @Singleton
    fun provideAdvertsApi(): AdvertsApi {
        return Retrofit.Builder()
            .baseUrl(AppModule.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideAdvertsRepository(api: AdvertsApi, sharedPreferences: SharedPreferences): AdvertsRepository {
        return AdvertsRepositoryImpl(api, sharedPreferences)
    }
}
