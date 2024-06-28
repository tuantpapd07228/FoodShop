package com.example.asmand103.di

import com.example.asmand103.api.ApiGhnServer
import com.example.asmand103.api.ApiServer
import com.example.asmand103.constants.Constants.BASE_URL
import com.example.asmand103.constants.Constants.BASE_URL_GHN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun ProvideServer() : ApiServer{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServer::class.java)
    }

    @Provides
    @Singleton
    fun ProvideRetrofitLab8() : ApiGhnServer =
        Retrofit.Builder()
            .baseUrl(BASE_URL_GHN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiGhnServer::class.java)
}