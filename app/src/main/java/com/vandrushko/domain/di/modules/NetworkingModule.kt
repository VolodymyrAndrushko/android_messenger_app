package com.vandrushko.domain.di.modules

import com.vandrushko.domain.network.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

private const val BASE_URL = "http://178.63.9.114:7777/api/"

@Module
@InstallIn(SingletonComponent::class)
class NetworkingModule @Inject constructor() {

    @Provides
    fun providesRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesService(retrofit: Retrofit) : NetworkService {
        return retrofit.create(NetworkService::class.java)
    }
}