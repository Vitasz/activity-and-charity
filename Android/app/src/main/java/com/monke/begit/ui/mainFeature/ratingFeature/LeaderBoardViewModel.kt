package com.monke.begit.ui.mainFeature.ratingFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.begit.domain.mockedUsers
import com.monke.begit.domain.repository.UserRepository
import com.monke.begit.ui.uiModels.LeaderboardUser
import javax.inject.Inject

class LeaderBoardViewModel(
    private val userRepository: UserRepository
) : ViewModel() {


    val leaderBoardUsers = mockedUsers.map { LeaderboardUser(it, it.login.length*120000) }.sortedByDescending { it.moneyEarned }

    class Factory @Inject constructor(
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LeaderBoardViewModel(
                userRepository = userRepository
            ) as T
        }
    }
}