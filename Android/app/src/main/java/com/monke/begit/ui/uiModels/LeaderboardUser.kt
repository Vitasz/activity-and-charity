package com.monke.begit.ui.uiModels

import com.monke.begit.domain.model.User

data class LeaderboardUser (
    val user: User,
    val moneyEarned: Int
)