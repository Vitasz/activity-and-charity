package com.monke.begit.domain.repository

import com.monke.begit.domain.model.User

interface UserRepository {

    fun getUser(): User

    fun saveUser(user: User)

    suspend fun getUserByEmail(email: String): Result<User?>

    suspend fun createUser(): Result<Any?>
    suspend fun createSupervisor(): Result<Any?>
    suspend fun loginUser(username: String, password: String): Result<Any?>
    suspend fun loginSupervisor(): Result<Any?>

    suspend fun getTop10(): Result<Any?>




}