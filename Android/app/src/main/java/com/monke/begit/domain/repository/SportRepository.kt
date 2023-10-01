package com.monke.begit.domain.repository

import com.monke.begit.domain.model.SportActivity

interface SportRepository {

    suspend fun getActivitiesList(): Result<Any?>

    fun setTrackedSportActivity(sportActivity: SportActivity)

    fun getTrackedSportActivity(): SportActivity?

    suspend fun addActivity(sportActivity: SportActivity): Result<Any?>

}