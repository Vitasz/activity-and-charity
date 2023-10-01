package com.monke.begit.ui.mainFeature.activityFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.begit.domain.model.SportActivity
import com.monke.begit.domain.repository.SportRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActivitiesViewModel(
    private val sportRepository: SportRepository
) : ViewModel() {


    val activities: List<SportActivity> = sportRepository.getActivitiesList()


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