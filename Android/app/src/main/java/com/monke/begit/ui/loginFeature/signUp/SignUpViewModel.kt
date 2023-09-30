package com.monke.begit.ui.loginFeature.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.begit.domain.model.AccountType
import com.monke.begit.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.math.log

class SignUpViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var accountType = AccountType.Employee

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _login = MutableStateFlow("")
    val login = _login.asStateFlow()

    private val _dataIsValid = MutableStateFlow(false)
    var dataIsValid = _dataIsValid.asStateFlow()

    fun setEmail(email: String) {
        _email.value = email
        _dataIsValid.value = email.isNotEmpty() && login.value.isNotEmpty()
    }

    fun setLogin(login: String) {
        _login.value = login
        _dataIsValid.value = email.value.isNotEmpty() && login.isNotEmpty()
    }

    fun saveData() {
        val user = userRepository.getUser().copy(
            login = login.value,
            email = email.value,
            accountType = accountType
        )
        userRepository.saveUser(user)
    }

    class Factory @Inject constructor(
        private val userRepository: UserRepository
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignUpViewModel(
                userRepository = userRepository
            ) as T
        }
    }
}