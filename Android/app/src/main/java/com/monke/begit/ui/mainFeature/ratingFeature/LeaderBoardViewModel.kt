package com.monke.begit.ui.mainFeature.ratingFeature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.begit.data.remote.UserTop
import com.monke.begit.domain.mockedUsers
import com.monke.begit.domain.model.User
import com.monke.begit.domain.repository.UserRepository
import com.monke.begit.ui.uiModels.LeaderboardUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LeaderBoardViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private var _leaderBoardUsers = MutableStateFlow<List<LeaderboardUser>>(ArrayList<LeaderboardUser>())
    var leaderBoardUsers = _leaderBoardUsers.asStateFlow()
     init {
        viewModelScope.launch{
            while (true){
                var x = userRepository.getTop10()
                if (x.isSuccess){
                    Log.d("GET TOP", "SUCCESS")
                    val result = x.getOrNull() as ArrayList<UserTop>
                    _leaderBoardUsers.value = result.map { LeaderboardUser(it.name, it.value.toInt()) }.sortedByDescending { it.moneyEarned }
                }
                else{
                    Log.d("GET TOP", "FAILURE")
                }
                delay(10*1000)
            }

        }
    }
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