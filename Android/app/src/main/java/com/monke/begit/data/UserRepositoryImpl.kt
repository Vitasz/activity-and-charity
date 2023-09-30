package com.monke.begit.data

import com.monke.begit.domain.mockedUsers
import com.monke.begit.domain.model.User
import com.monke.begit.domain.repository.UserRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {

    private var user: User? = null

    override fun getAuthorizedUser(): User? {
        return user
    }

    override suspend fun createUser(user: User) {
        this.user = user
    }

    override suspend fun getUserByEmail(email: String): Result<User?> {
        return Result.success(mockedUsers.find { it.email == email })
    }

}