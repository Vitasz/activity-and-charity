package com.monke.begit.ui.loginFeature.signUp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.begit.domain.model.Subdivision
import com.monke.begit.domain.repository.SubdivisionRepository
import com.monke.begit.domain.repository.UserRepository
import com.monke.begit.ui.uiModels.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class SupervisorSignUpViewModel (
    private val userRepository: UserRepository
) : ViewModel() {

    var name = ""
        set(value) {
            field = value
            checkDataForValid()
        }
    var surname = ""
        set(value) {
            field = value
            checkDataForValid()
        }
    var password = ""
        set(value) {
            field = value
            checkDataForValid()
        }
    var repeatedPassword = ""
        set(value) {
            field = value
            checkDataForValid()
        }


    private val _dataValid = MutableStateFlow(false)
    val dataValid = _dataValid.asStateFlow()

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState = _uiState.asStateFlow()

    private fun checkDataForValid() {
        _dataValid.value = name.isNotEmpty() &&
                surname.isNotEmpty() &&
                password.isNotEmpty() &&
                repeatedPassword.isNotEmpty() &&
                password == repeatedPassword
    }

    fun signUp() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val user = userRepository.getUser().copy(
                name = name,
                surname = surname,
                password = password,
            )
            userRepository.saveUser(user)
            val creationRequest = userRepository.createSupervisor()
            if (creationRequest.isSuccess)
                _uiState.value = UiState.Success()
            else
                _uiState.value = UiState.Error(creationRequest.exceptionOrNull()!!)
        }
    }

    class Factory @Inject constructor(
        private val userRepository: UserRepository,
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SupervisorSignUpViewModel(
                userRepository = userRepository,
            ) as T
        }
    }
}