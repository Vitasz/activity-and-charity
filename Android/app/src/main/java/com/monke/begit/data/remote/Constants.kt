package com.monke.begit.data.remote

import com.google.android.gms.fitness.FitnessActivities
import com.google.android.gms.fitness.data.DataType

class Constants {
    companion object{
        const val APIUrl = "http://89.185.85.184:8000/"
        val FITNESS_ACTIVITIES = arrayListOf<String>(FitnessActivities.RUNNING, FitnessActivities.BIKING,
                                                     FitnessActivities.WALKING, FitnessActivities.STRENGTH_TRAINING)

        val DATA_TYPES = arrayListOf<DataType>(DataType.TYPE_DISTANCE_DELTA, DataType.TYPE_DISTANCE_DELTA,
                                       DataType.TYPE_DISTANCE_DELTA, DataType.TYPE_CALORIES_EXPENDED )
    }
}