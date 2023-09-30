package com.monke.begit.data

import com.monke.begit.domain.mockedUsers
import com.monke.begit.domain.model.AccountType
import com.monke.begit.domain.model.Subdivision
import com.monke.begit.domain.model.User
import com.monke.begit.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {

    private var user: User = User(
        id = 0,
        name = "",
        surname = "",
        login = "",
        password = "",
        email = "",
        accountType = AccountType.Employee,
        subdivision = Subdivision(id = 0, name = "", code = "")
    )

    override fun getUser(): User {
        return user
    }

    override fun saveUser(user: User) {
        this.user = user
    }

    override suspend fun getUserByEmail(email: String): Result<User?> {
        return Result.success(mockedUsers.find { it.email == email })
    }

    override fun createUser(): Result<Any?> {
        user = user.copy(id = 1)
        return Result.success(null)
    }

}