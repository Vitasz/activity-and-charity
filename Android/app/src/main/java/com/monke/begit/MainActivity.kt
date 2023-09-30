package com.monke.begit

import android.Manifest
import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.Manifest.permission.CAMERA
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.uwasting.data.LocalDateDeserializer
import com.example.uwasting.data.remote.API
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.Fitness.getSessionsClient
import com.google.android.gms.fitness.FitnessActivities
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.fitness.request.SessionReadRequest
import com.google.gson.GsonBuilder
import com.monke.begit.data.remote.Constants
import com.monke.begit.data.remote.GoogleFitAPI
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit


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