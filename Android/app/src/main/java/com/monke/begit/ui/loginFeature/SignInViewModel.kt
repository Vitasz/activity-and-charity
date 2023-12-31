package com.monke.begit.ui.loginFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.begit.data.remote.GetUserRemote
import com.monke.begit.domain.model.AccountType
import com.monke.begit.domain.model.User
import com.monke.begit.domain.repository.UserRepository
import com.monke.begit.ui.uiModels.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _login = MutableStateFlow("")
    val login = _login.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState = _uiState.asStateFlow()

    private val _isDataValid = MutableStateFlow(false)
    val isDataValid = _isDataValid.asStateFlow()


    fun setLogin(login: String) {
        _login.value = login
        _isDataValid.value = login.isNotEmpty() && password.value.isNotEmpty()

    }

    fun setPassword(password: String) {
        _password.value = password
        _isDataValid.value = password.isNotEmpty() && login.value.isNotEmpty()
    }


    fun signIn() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = userRepository.loginUser(
                username = login.value,
                password = password.value
            )
            if (result.isSuccess) {
                val user = result.getOrNull() as GetUserRemote
                userRepository.saveUser(User.fromRemote(user, AccountType.Employee))
                _uiState.value = UiState.Success()
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