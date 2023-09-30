package com.monke.begit

import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.uwasting.data.LocalDateDeserializer
import com.example.uwasting.data.remote.API
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.gson.GsonBuilder
import com.monke.begit.data.remote.Constants

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.concurrent.TimeUnit


private const val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1001
class MainActivity : AppCompatActivity() {
    private lateinit var api: API
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //configureRetrofit()
        //setupGoogleFit()
        //api.getActivities(1)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        textView.text = "HI"
        setupGoogleFit()

    }

    private lateinit var fitnessOptions: FitnessOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    private fun setupGoogleFit() {
        val cc = ContextCompat.checkSelfPermission(this, ACTIVITY_RECOGNITION)
        if (cc
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(ACTIVITY_RECOGNITION),
                1)
            // Permission is not granted
        }

        fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build()

        val account = GoogleSignIn.getAccountForExtension(this, fitnessOptions)
        googleSignInClient =
            GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)

        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                this,
                GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                account,
                fitnessOptions
            )
        } else {
            val readRequest = DataReadRequest.Builder()
                .read(DataType.TYPE_STEP_COUNT_DELTA)
                .setTimeRange(
                    System.currentTimeMillis() - 100000,
                    System.currentTimeMillis(),
                    TimeUnit.MILLISECONDS
                )
                .build()
            account.let {
                Fitness.getHistoryClient(this, it)
                    .readData(readRequest)
                    .addOnSuccessListener { response ->
                        val dataSet = response.getDataSet(DataType.TYPE_STEP_COUNT_DELTA)
                        val totalSteps =
                            if (dataSet.isEmpty) 0 else dataSet.getDataPoints().sumBy {
                                it.getValue(
                                    Field.FIELD_STEPS
                                ).asInt()
                            }

                        // Print the total number of steps in a view
                        textView.text = "Total Steps: $totalSteps"
                    }
                    .addOnFailureListener { e ->
                        Log.e("ERROR", "There was a problem reading step count data.", e)
                    }
            }
        }


        /*
        val listener = OnDataPointListener { dataPoint ->
            for (field in dataPoint.dataType.fields) {
                val value = dataPoint.getValue(field)
                val stepCount = value.asInt()
                Log.d("STEPS:", stepCount.toString())
            }
        }

        Fitness.getHistoryClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener { dataSet ->
                for (dataPoint in dataSet.dataPoints) {
                    for (field in dataPoint.dataType.fields) {
                        val value = dataPoint.getValue(field)
                        val stepCount = value.asInt()
                        // Handle initial step count
                    }
                }
            }

        Fitness.getRecordingClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
            .subscribe(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener {
                // Successfully subscribed to step count updates
            }*/
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fitnessOptions = FitnessOptions.builder()
                    .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                    .build()

                val account = GoogleSignIn.getAccountForExtension(this, fitnessOptions)
                googleSignInClient =
                    GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)

                if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
                    GoogleSignIn.requestPermissions(
                        this,
                        GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                        account,
                        fitnessOptions
                    )
                } else {
                    val readRequest = DataReadRequest.Builder()
                        .read(DataType.TYPE_STEP_COUNT_DELTA)
                        .setTimeRange(
                            System.currentTimeMillis() - 100000,
                            System.currentTimeMillis(),
                            TimeUnit.MILLISECONDS
                        )
                        .build()
                    account.let {
                        Fitness.getHistoryClient(this, it)
                            .readData(readRequest)
                            .addOnSuccessListener { response ->
                                val dataSet = response.getDataSet(DataType.TYPE_STEP_COUNT_DELTA)
                                val totalSteps =
                                    if (dataSet.isEmpty) 0 else dataSet.getDataPoints().sumBy {
                                        it.getValue(
                                            Field.FIELD_STEPS
                                        ).asInt()
                                    }

                                // Print the total number of steps in a view
                                textView.text = "Total Steps: $totalSteps"
                            }
                            .addOnFailureListener { e ->
                                Log.e("ERROR", "There was a problem reading step count data.", e)
                            }
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                subscribeToStepCountUpdates()
            } else {
                Log.e("FUCK", resultCode.toString())
            }
        }
    }

    private fun subscribeToStepCountUpdates() {
        val client = Fitness.getRecordingClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
        client.subscribe(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Successfully subscribed to step count updates
                } else {
                    // Handle subscription failure
                }
            }
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