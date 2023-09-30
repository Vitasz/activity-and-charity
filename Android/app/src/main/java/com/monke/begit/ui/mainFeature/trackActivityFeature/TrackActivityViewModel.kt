package com.monke.begit.ui.mainFeature.trackActivityFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.begit.ui.uiModels.SportActivityState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

class TrackActivityViewModel : ViewModel() {

    private val _sportActivityState = MutableStateFlow(SportActivityState.Play)
    val sportActivityState = _sportActivityState.asStateFlow()

    private val _timeSeconds = MutableStateFlow(0)
    val timeSeconds = _timeSeconds.asStateFlow()

    private lateinit var stopWatchJob: Job


    init {
        stopWatchJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                _timeSeconds.value = _timeSeconds.value + 1
            }
        }
        viewModelScope.launch {
            _sportActivityState.collect { state ->
                when (state) {
                    SportActivityState.Play -> {
                        if (!stopWatchJob.isActive) {
                            stopWatchJob = viewModelScope.launch {
                                while (true) {
                                    delay(1000L)
                                    _timeSeconds.value = _timeSeconds.value + 1
                                }
                            }
                        }
                    }
                    SportActivityState.Pause -> {
                        stopWatchJob.cancel()
                    }
                }
            }
        }
    }

    fun setSportActivityState(state: SportActivityState) {
        _sportActivityState.value = state
    }

    fun stopSportActivity() {
        stopWatchJob.cancel()
    }

    class Factory @Inject constructor(

    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrackActivityViewModel(

            ) as T
        }
    }
}