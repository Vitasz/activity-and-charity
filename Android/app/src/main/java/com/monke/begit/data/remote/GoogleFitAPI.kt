package com.monke.begit.data.remote

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Session
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.request.DataSourcesRequest
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.fitness.request.SensorRequest
import com.google.android.gms.fitness.result.SessionStopResult
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.monke.begit.MainActivity
import com.monke.begit.di.AppScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import java.util.concurrent.TimeUnit

class GoogleFitAPI(val context: MainActivity) {
    private lateinit var fitnessOptions: FitnessOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var account: GoogleSignInAccount
    private lateinit var googleApiClient: GoogleApiClient
    private var currentSession: Session? = null
    private var listener: OnDataPointListener? = null
    var sessionSummary: Int = 0
    @SuppressLint("SetTextI18n")
    fun setupGoogleFit() {


        fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_SPEED, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_LOCATION_SAMPLE, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_ACTIVITY_SEGMENT, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_ACTIVITY_SUMMARY, FitnessOptions.ACCESS_READ)
            .accessActivitySessions(FitnessOptions.ACCESS_WRITE)
            .accessActivitySessions(FitnessOptions.ACCESS_READ
            )
            .build()
        account = GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        googleSignInClient =
            GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN)
        googleApiClient = googleSignInClient.asGoogleApiClient()

        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                context,
                1001,
                account,
                fitnessOptions
            )
        }
    }

    fun dumpDataSet(dataSet: DataSet) {
        Log.i("TAG", "Data returned for Data type: ${dataSet.dataType.name}")
        for (dp in dataSet.dataPoints) {
            Log.i("TAG","Data point:")
            Log.i("TAG","\tType: ${dp.dataType.name}")
            Log.i("TAG","\tStart: ${dp.getStartTimeString()}")
            Log.i("TAG","\tEnd: ${dp.getEndTimeString()}")
            for (field in dp.dataType.fields) {
                Log.i("TAG","\tField: ${field.name.toString()} Value: ${dp.getValue(field)}")
            }
        }
    }

    fun DataPoint.getStartTimeString() = Instant.ofEpochSecond(this.getStartTime(TimeUnit.SECONDS))
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime().toString()

    fun DataPoint.getEndTimeString() = Instant.ofEpochSecond(this.getEndTime(TimeUnit.SECONDS))
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime().toString()
    fun checkDevicesAndStartSession(activity:String, dataType: DataType){
        Fitness.getSensorsClient(context, account)
            .findDataSources(
                DataSourcesRequest.Builder()
                    .setDataTypes(dataType)
                   // .setDataSourceTypes(DataSource.TYPE_DERIVED)
                    .build()
            )
            .addOnSuccessListener { dataSources ->
                Log.i("TAG", "Listener Success")
                Log.i("TAG", "DataSources: " + dataSources.size)
                for (dataSource in dataSources) {
                    Log.i("TAG", "Data source found: $dataSource")
                    Log.i("TAG", "Data Source type: " + dataSource.dataType.name)

                    // Let's register a listener to receive Activity data!
                    if (dataSource.dataType == dataType) {
                        Log.i(
                            "TAG",
                            "Data source for ${dataType.name} found!  Registering."
                        )
                        startSession(activity, dataType, dataSource)
                    }

                }
            }
            .addOnFailureListener { e -> Log.e("TAG", "failed", e) }
        }
    private fun startSession(activity: String, dataType: DataType, dataSource: DataSource) {
        val startTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()
        val id = UUID.randomUUID().toString()
        Fitness.getRecordingClient(context, account)
            // This example shows subscribing to a DataType, across all possible data
            // sources. Alternatively, a specific DataSource can be used.
            .subscribe(dataType)
            .addOnSuccessListener {
                Log.i("TAG", "Successfully subscribed!")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "There was a problem subscribing.", e)
            }
        currentSession = Session.Builder()
            .setIdentifier(id)
            .setActivity(activity)
            .setStartTime(startTime, TimeUnit.SECONDS)

            .build()
        Fitness.getSessionsClient(context, account)
            .startSession(currentSession!!)
            .addOnSuccessListener {
                Log.i("TAG", "Session started successfully!")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "There was an error starting the session", e)
            }

        listener = OnDataPointListener { dataPoint ->
            for (field in dataPoint.dataType.fields) {
                val value = dataPoint.getValue(field)
                Log.i("TAG", "Detected DataPoint field: ${field.name}")
                Log.i("TAG", "Detected DataPoint value: $value")
                try {
                    sessionSummary += value.asInt()
                } catch (e: Exception) {

                }
            }
        }
        Fitness.getSensorsClient(context, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
            .add(
                SensorRequest.Builder()
                    .setDataSource(dataSource) // Optional but recommended for custom
                    // data sets.
                    .setDataType(dataType) // Can't be omitted.
                    .setSamplingRate(1, TimeUnit.SECONDS)
                    .build(),
                listener!!
            )
            .addOnSuccessListener {
                Log.i("TAG", "Listener registered!")
            }
            .addOnFailureListener {
                Log.e("TAG", "Listener not registered.")
            }

    }

    fun EndSession() {
        if (currentSession != null) {
            //TODO SEND ACTIVITY TO SERVER
            Log.d("TAG", "Session summary: $sessionSummary")
            Fitness.getSessionsClient(context, account)
                .stopSession(currentSession!!.identifier)
                .addOnSuccessListener {
                    Log.i("TAG", "Session stopped successfully!")
                    Fitness.getRecordingClient(context, account)
                        .listSubscriptions()
                        .addOnSuccessListener { subscriptions ->
                            for (sc in subscriptions) {
                                val dt = sc.dataType

                                //Log.i("TAG", "Value for data type: ${listener}")
                            }
                        }
                    currentSession = null

                    Fitness.getRecordingClient(context, account)
                        // This example shows unsubscribing from a DataType. A DataSource should
                        // be used where the subscription was to a DataSource. Alternatively, if
                        // the client doesn't maintain its subscription information, they can use
                        // an element from the return value of listSubscriptions(), which is of
                        // type Subscription.
                        .unsubscribe(DataType.TYPE_STEP_COUNT_DELTA)
                        .addOnSuccessListener {

                            Log.i("TAG", "Successfully unsubscribed.")
                        }
                        .addOnFailureListener { e ->
                            Log.w("TAG", "Failed to unsubscribe.")
                            // Retry the unsubscribe request.
                        }

                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "There was an error stopping the session", e)
                    currentSession = null
                }
            Fitness.getSensorsClient(context, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
                .remove(listener!!)
                .addOnSuccessListener {
                    Log.i("TAG", "Listener was removed!")
                }
                .addOnFailureListener {
                    Log.i("TAG", "Listener was not removed.")
                }

            sessionSummary = 0
        }
    }

}