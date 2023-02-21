package com.alibasoglu.ciftlikpazarimandroid.user.di

import com.alibasoglu.ciftlikpazarimandroid.di.AppModule
import com.alibasoglu.ciftlikpazarimandroid.user.data.UserApi
import com.alibasoglu.ciftlikpazarimandroid.user.data.UserRepositoryImpl
import com.alibasoglu.ciftlikpazarimandroid.user.domain.UserRepository
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
object UserModule {

    @Provides
    @Singleton
    fun provideUserApi(okHttpClient: OkHttpClient): UserApi {
        return Retrofit.Builder()
            .baseUrl(AppModule.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: UserApi): UserRepository {
        return UserRepositoryImpl(api = api)
    }
}
