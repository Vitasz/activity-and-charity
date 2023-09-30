package com.monke.begit.ui.loginFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.begit.domain.exceptions.IncorrectPasswordException
import com.monke.begit.domain.exceptions.NoUserFoundException
import com.monke.begit.domain.repository.UserRepository
import com.monke.begit.ui.uiModels.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState = _uiState.asStateFlow()

    private val _isDataValid = MutableStateFlow(false)
    val isDataValid = _isDataValid.asStateFlow()


    fun setEmail(emailAddress: String) {
        _email.value = emailAddress
        _isDataValid.value = emailAddress.isNotEmpty() && password.value.isNotEmpty()

    }

    fun setPassword(password: String) {
        _password.value = password
        _isDataValid.value = password.isNotEmpty() && email.value.isNotEmpty()
    }


    fun signIn() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = userRepository.getUserByEmail(email.value)
            if (result.isSuccess) {
                val user = result.getOrNull()
                if (user == null)
                    _uiState.value = UiState.Error(NoUserFoundException())
                else if (user.password == password.value )
                    _uiState.value = UiState.Success()
                else
                    _uiState.value = UiState.Error(IncorrectPasswordException())
            } else
                result.exceptionOrNull()?.let { _uiState.value = UiState.Error(it) }


        }
    }

    class Factory @Inject constructor(
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignInViewModel(userRepository) as T
        }

    }
}