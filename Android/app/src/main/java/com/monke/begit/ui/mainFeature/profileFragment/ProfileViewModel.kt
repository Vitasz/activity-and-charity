package com.monke.begit.ui.mainFeature.profileFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.begit.domain.repository.UserRepository
import javax.inject.Inject

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {


    val user = userRepository.getUser()


    class Factory @Inject constructor(
        private val userRepository: UserRepository
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(
                userRepository = userRepository
            ) as T
        }
    }
}