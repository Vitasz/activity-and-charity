package com.monke.begit.ui.mainFeature.activityFeature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.begit.data.remote.Constants
import com.monke.begit.data.remote.PhysicalActivity
import com.monke.begit.domain.model.SportActivity
import com.monke.begit.domain.repository.SportRepository
import com.monke.begit.ui.uiModels.LeaderboardUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActivitiesViewModel(
    private val sportRepository: SportRepository
) : ViewModel() {

    private var _activities = MutableStateFlow<List<SportActivity>>(ArrayList<SportActivity>())
    var activities = _activities.asStateFlow()

    init {
        viewModelScope.launch{
            while (true){
                var x = sportRepository.getActivitiesList()
                if (x.isSuccess){
                    Log.d("GET TOP", "SUCCESS")
                    val result = x.getOrNull() as ArrayList<PhysicalActivity>
                    _activities.value = result.map {
                        SportActivity(Constants.Companion.ACTIVITIES_NAMES[it.idType-1].name, it.idType-1, it.value.toInt())
                    }
                }
                else{
                    Log.d("GET TOP", "FAILURE")
                }
                delay(10*1000)
            }

        }
    }
    class Factory @Inject constructor(
        private val sportRepository: SportRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ActivitiesViewModel(
                sportRepository = sportRepository
            ) as T
        }
    }
}