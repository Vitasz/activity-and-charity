package com.monke.begit.data.remote

import com.google.android.gms.fitness.FitnessActivities
import com.google.android.gms.fitness.data.DataType
import com.monke.begit.domain.model.SportActivity

class Constants {
    companion object{
        const val APIUrl = "http://89.185.85.184:5000/"
        val FITNESS_ACTIVITIES = arrayListOf<String>(FitnessActivities.RUNNING, FitnessActivities.BIKING,
                                                     FitnessActivities.WALKING, FitnessActivities.STRENGTH_TRAINING)

        val DATA_TYPES = arrayListOf<DataType>(DataType.TYPE_STEP_COUNT_DELTA, DataType.TYPE_DISTANCE_DELTA,
                                       DataType.TYPE_DISTANCE_DELTA, DataType.TYPE_CALORIES_EXPENDED )

        val ACTIVITIES_NAMES = arrayListOf(SportActivity("Бег", 0), SportActivity("Езда на велосипеде", 1),
                                            SportActivity("Ходьба", 2), SportActivity("Силовая тренировка", 3))
    }
}