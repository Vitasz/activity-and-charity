package com.monke.begit

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.uwasting.data.LocalDateDeserializer
import com.monke.begit.data.remote.API
import com.google.gson.GsonBuilder
import com.monke.begit.data.remote.Constants
import com.monke.begit.data.remote.GoogleFitAPI

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate


private const val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1001
class MainActivity : AppCompatActivity() {
    private lateinit var api: API
    private lateinit var googleFitAPI: GoogleFitAPI

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //configureRetrofit()
        //setupGoogleFit()
        //api.getActivities(1)
        setContentView(R.layout.activity_main)



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION,
Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                1)
            // Permission is not granted
        }



        googleFitAPI = GoogleFitAPI(this)
        //x.sendRequest(DataType.TYPE_STEP_COUNT_DELTA, LocalDateTime.now().atZone(System),0,TimeUnit.MILLISECONDS)
        googleFitAPI.setupGoogleFit()
    }

    private fun startActivity(){
        googleFitAPI.checkDevicesAndStartSession(Constants.FITNESS_ACTIVITIES[2], Constants.DATA_TYPES[2])
    }
    private fun endActivity(){
        googleFitAPI.EndSession()
    }
    // Настройка подключения к серверу
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