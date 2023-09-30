package com.monke.begit.ui.loginFeature.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.begit.domain.model.AccountType
import javax.inject.Inject

class SignUpViewModel : ViewModel() {

    var accountType = AccountType.Employee

    class Factory @Inject constructor(): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignUpViewModel() as T
        }
    }
}