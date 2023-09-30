package com.monke.begit.domain.repository

import com.monke.begit.domain.model.User

interface UserRepository {

    fun getAuthorizedUser(): User?

    suspend fun createUser(user: User)

    suspend fun getUserByEmail(email: String): Result<User?>

}