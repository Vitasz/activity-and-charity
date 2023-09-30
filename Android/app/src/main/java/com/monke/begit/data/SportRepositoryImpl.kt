package com.monke.begit.data

import com.monke.begit.di.AppScope
import com.monke.begit.domain.mockedActivities
import com.monke.begit.domain.model.SportActivity
import com.monke.begit.domain.repository.SportRepository
import javax.inject.Inject

@AppScope
class SportRepositoryImpl @Inject constructor(): SportRepository {


    private var trackedSportActivity : SportActivity? = null

    override suspend fun getActivitiesList(): Result<List<SportActivity>> {
        return Result.success(mockedActivities)
    }

    override fun setTrackedSportActivity(sportActivity: SportActivity) {
        trackedSportActivity = sportActivity
    }

    override fun getTrackedSportActivity() = trackedSportActivity


}