package com.monke.begit.domain.repository

import com.monke.begit.domain.model.SportActivity

interface SportRepository {

    suspend fun getActivitiesList(): Result<List<SportActivity>>

    fun setTrackedSportActivity(sportActivity: SportActivity)

    fun getTrackedSportActivity(): SportActivity?

}