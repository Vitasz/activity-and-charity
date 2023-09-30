package com.monke.begit.data

import androidx.lifecycle.viewmodel.viewModelFactory
import com.monke.begit.data.remote.API
import com.monke.begit.di.AppScope
import com.monke.begit.domain.exceptions.IncorrectPasswordException
import com.monke.begit.domain.mockedUsers
import com.monke.begit.domain.model.AccountType
import com.monke.begit.domain.model.Subdivision
import com.monke.begit.domain.model.User
import com.monke.begit.domain.repository.UserRepository
import java.security.MessageDigest
import java.util.Base64
import javax.inject.Inject

@AppScope
class UserRepositoryImpl @Inject constructor(
    private val api: API
) : UserRepository {

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
    fun String.md5(): String {
        return hashString(this, "MD5")
    }

    fun String.sha256(): String {
        return hashString(this, "SHA-256")
    }

    private fun hashString(input: String, algorithm: String): String {
        return MessageDigest
            .getInstance(algorithm)
            .digest(input.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }

    override suspend fun createUser(): Result<Any?> {
        val response = api.registerUser(API.RequestBodyRegister(
            user.login, hashString(user.password, "SHA-256"), user.email, user.name
        ))


        return if (response.isSuccessful){
            Result.success(response.body())
        } else{
            Result.failure(IncorrectPasswordException())
        }
    }

    override suspend fun createSupervisor(): Result<Any?> {
        val response = api.registerSupervisor(API.RequestBodyRegister(
            user.login, hashString(user.password, "SHA-256"), user.email, user.name
        ))


        return if (response.isSuccessful){
            Result.success(response.body())
        } else{
            Result.failure(IncorrectPasswordException())
        }
    }

    override suspend fun loginUser(): Result<Any?>{
        val response = api.loginUser(
            API.RequestBodyLogin("johnsmith", hashString("password123", "SHA-256"))
        )
        return if (response.isSuccessful){
            Result.success(response.body())
        } else{
            Result.failure(IncorrectPasswordException())
        }
    }

    override suspend fun loginSupervisor(): Result<Any?> {
        val response = api.loginSupervisor(
            API.RequestBodyLogin(user.login, hashString(user.password, "SHA-256"))
        )
        return if (response.isSuccessful){
            Result.success(response.body())
        } else{
            Result.failure(IncorrectPasswordException())
        }
    }

}