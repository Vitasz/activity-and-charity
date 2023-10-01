package com.monke.begit.domain.repository

import com.monke.begit.domain.model.SportActivity

interface SportRepository {

    fun getActivitiesList(): List<SportActivity>

    fun setTrackedSportActivity(sportActivity: SportActivity)

    fun getTrackedSportActivity(): SportActivity?

    suspend fun addActivity(sportActivity: SportActivity): Result<Any?>

}