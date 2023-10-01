package com.monke.begit.data

import android.util.Log
import com.monke.begit.data.remote.API
import com.monke.begit.di.AppScope
import com.monke.begit.domain.exceptions.IncorrectPasswordException
import com.monke.begit.domain.model.SportActivity
import com.monke.begit.domain.repository.SportRepository
import com.monke.begit.getTodayDateString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.Calendar
import javax.inject.Inject

@AppScope
class SportRepositoryImpl @Inject constructor(
    private val api: API
): SportRepository {


    private var trackedSportActivity: SportActivity? = null

    private val activitiesList = ArrayList<SportActivity>()


    init {
        activitiesList.add(
            SportActivity(
                name = "Бег",
                id = 1,
                moneyEarned = 123
            )
        )
        activitiesList.add(
            SportActivity(
                name = "Силовая тренировка",
                id = 2,
                moneyEarned = 1233
            )
        )

    }


    override suspend fun getActivitiesList(): Result<Any?>{
        try
        {
            val res = api.getActivities()
            if (res.isSuccessful) {
                Log.d("SportRepository", "Success")
                return Result.success(res.body())
            } else {
                Log.d("SportRepository", res.message() + "${res.code()}")
                return Result.failure(IncorrectPasswordException())
            }
        } catch (e: Exception)
        {
            Log.e("EXCEPTION", e.message.toString())
            return Result.failure(e)
        }
    }



    override fun setTrackedSportActivity(sportActivity: SportActivity) {
        trackedSportActivity = sportActivity
    }

    override fun getTrackedSportActivity() = trackedSportActivity


    /*  override suspend fun addActivity(sportActivity: SportActivity): Result<Any?> {
        activitiesList.add(sportActivity)
        return Result.success(null)
    }*/


    override suspend fun addActivity(sportActivity: SportActivity): Result<Any?> {
        try {
            val res = api.addActivity(
                API.RequestBodyAddActivity(
                    typeId = sportActivity.id + 1,
                    value = sportActivity.moneyEarned,
                    date = "2002-09-09"
                )
            )
            if (res.isSuccessful) {
                Log.d("SportRepository", "Success")
                return Result.success(null)
            } else {
                Log.d("SportRepository", res.message() + "${res.code()}")
                return Result.failure(IncorrectPasswordException())
            }
        } catch (e: Exception) {
            Log.e("EXCEPTION", e.message.toString())
            return Result.failure(e)
        }
    }
}