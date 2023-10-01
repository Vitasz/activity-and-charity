package com.monke.begit.ui.mainFeature.activityFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.begit.data.remote.Constants
import com.monke.begit.domain.model.SportActivity
import com.monke.begit.domain.repository.SportRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectActivityViewModel (
    private val sportRepository: SportRepository
) : ViewModel() {

    lateinit var sportActivities: List<SportActivity>

    lateinit var selectedActivity: SportActivity

    init {
        viewModelScope.launch {
            //val request = sportRepository.getActivitiesList()
            //if (request.isSuccess) {
                sportActivities = Constants.ACTIVITIES_NAMES//request.getOrNull() ?: ArrayList<SportActivity>()
                selectedActivity = sportActivities[0]
            //}
        }
    }

    fun saveSportActivity() {
        sportRepository.setTrackedSportActivity(selectedActivity)
    }

    class Factory @Inject constructor(
        private val sportRepository: SportRepository
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SelectActivityViewModel(
                sportRepository = sportRepository
            ) as T
        }

    }
}