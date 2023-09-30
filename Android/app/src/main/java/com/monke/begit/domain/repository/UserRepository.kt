package com.monke.begit.domain.repository

import com.monke.begit.domain.model.User

interface UserRepository {

    fun getUser(): User

    fun saveUser(user: User)

    suspend fun getUserByEmail(email: String): Result<User?>

    fun createUser(): Result<Any?>



}