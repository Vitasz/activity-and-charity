package com.monke.begit.ui.mainFeature.trackActivityFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.begit.domain.repository.UserRepository
import com.monke.begit.ui.mainFeature.profileFragment.ProfileViewModel
import com.monke.begit.ui.uiModels.SportActivityState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TrackActivityViewModel : ViewModel() {

    private val _sportActivityState = MutableStateFlow(SportActivityState.Play)
    val sportActivityState = _sportActivityState.asStateFlow()

    private val _seconds = MutableStateFlow(0)
    val seconds = _seconds.asStateFlow()

    

    init {

    }

    fun setSportActivityState(state: SportActivityState) {
        _sportActivityState.value = state
    }

    fun stopActivity() {

    }

    class Factory @Inject constructor(

    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrackActivityViewModel(

            ) as T
        }
    }
}