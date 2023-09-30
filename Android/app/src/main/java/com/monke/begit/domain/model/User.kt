package com.monke.begit.domain.model

data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val login: String,
    val password: String,
    val email: String,
    val accountType: AccountType,
    val subdivision: Subdivision
)