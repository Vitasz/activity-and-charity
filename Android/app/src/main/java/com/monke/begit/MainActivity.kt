package com.monke.begit

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.annotation.RequiresApi
import com.example.uwasting.data.LocalDateDeserializer
import com.example.uwasting.data.remote.API
import com.google.gson.GsonBuilder
import com.monke.begit.data.remote.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var api: API
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureRetrofit()
        api.getActivities(1)
        setContentView(R.layout.activity_main)
    }


    // Натсройка подключения к серверу
    private fun configureRetrofit() {
        val gson = GsonBuilder().registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer()).create()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.APIUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        api = retrofit.create(API::class.java)

    }
}