package com.monke.begit.data

import android.util.Log
import com.monke.begit.data.remote.API
import com.monke.begit.data.remote.GetUserRemote
import com.monke.begit.data.remote.LoginUserRemote
import com.monke.begit.di.AppScope
import com.monke.begit.domain.exceptions.IncorrectPasswordException
import com.monke.begit.domain.mockedUsers
import com.monke.begit.domain.model.AccountType
import com.monke.begit.domain.model.Subdivision
import com.monke.begit.domain.model.User
import com.monke.begit.domain.repository.UserRepository
import com.monke.begit.hashString
import java.lang.Exception
import java.security.MessageDigest
import java.util.Base64
import javax.inject.Inject

@AppScope
class UserRepositoryImpl @Inject constructor(
    private val api: API
) : UserRepository {

    private var user: User = User(
        name = "",
        surname = "",
        login = "",
        password = "",
        email = "",
        accountType = AccountType.Employee,
    )

    init {
        Log.d("UserRepository", "init")
    }

    override fun getUser(): User {
        return user
    }

    override fun saveUser(user: User) {
        this.user = user
    }

    override suspend fun getUserByEmail(email: String): Result<User?> {

        return Result.success(mockedUsers.find { it.email == email })
    }

    override suspend fun createUser(): Result<Any?> {
        return try {
            val response = api.registerUser(User.toRemote(user))

            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Log.e("Error", response.message() + "${response.code()}")
                Result.failure(IncorrectPasswordException())
            }
        } catch(e: Exception) {
            Log.e("EXCEPTION", e.message.toString())
            //TODO replace with can;t connet exception
            Result.failure(IncorrectPasswordException())
        }
    }

    override suspend fun createSupervisor(): Result<Any?> {
        return try {
            val response = api.registerSupervisor(User.toRemote(user))
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Log.e("Error", response.message() + "${response.code()}")
                Result.failure(IncorrectPasswordException())
            }
        } catch(e: Exception){
            Log.e("EXCEPTION", e.message.toString())
            //TODO replace with can;t connet exception
            Result.failure(IncorrectPasswordException())
        }
    }

    override suspend fun loginUser(username: String, password: String): Result<Any?>{
        return Result.success(GetUserRemote(username, "test@exmaple.com", "testname", -1, -1))
        try {
            val response = api.loginUser(
                LoginUserRemote(
                    username = username,
                    password = hashString(password, "SHA-256")
                )
            )

            return if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Log.e("Error", response.message() + "${response.code()}")
                Result.failure(IncorrectPasswordException())
            }
        }
        catch(e: Exception){
            Log.e("EXCEPTION", e.message.toString())
            //TODO replace with can;t connet exception
            return Result.failure(IncorrectPasswordException())
        }
    }

    override suspend fun loginSupervisor(): Result<Any?> {
        return try {
            val response = api.loginSupervisor(User.toLoginUser(user))
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Log.e("Error", response.message() + "${response.code()}")
                Result.failure(IncorrectPasswordException())
            }
        } catch(e: Exception){
            Log.e("EXCEPTION", e.message.toString())
            //TODO replace with can;t connect exception
            Result.failure(IncorrectPasswordException())
        }
    }

}