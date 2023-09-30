package com.monke.begit.di

import com.monke.begit.data.remote.API
import com.monke.begit.data.remote.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor())

        return Retrofit.Builder().baseUrl(Constants.APIUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): API = retrofit.create(API::class.java)


}