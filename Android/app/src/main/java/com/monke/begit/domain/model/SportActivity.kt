package com.monke.begit.domain.model

data class SportActivity(
    val name: String,
    val id: Int,
    val moneyEarned: Int = 0
)