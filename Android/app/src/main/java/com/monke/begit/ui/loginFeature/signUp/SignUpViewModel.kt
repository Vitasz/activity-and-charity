package com.monke.begit.ui.loginFeature.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.begit.domain.model.AccountType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.math.log

class SignUpViewModel : ViewModel() {

    var accountType = AccountType.Employee

    private val _email = MutableStateFlow<String?>("")
    val email = _email.asStateFlow()

    private val _login = MutableStateFlow<String?>("")
    val login = _login.asStateFlow()

    private val _dataIsValid = MutableStateFlow(false)
    var dataIsValid = _dataIsValid.asStateFlow()

    fun setEmail(email: String?) {
        _email.value = email
        _dataIsValid.value = !email.isNullOrEmpty() && !login.value.isNullOrEmpty()
    }

    fun setLogin(login: String?) {
        _login.value = login
        _dataIsValid.value = !email.value.isNullOrEmpty() && !login.isNullOrEmpty()
    }

    class Factory @Inject constructor(): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignUpViewModel() as T
        }
    }
}