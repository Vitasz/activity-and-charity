package com.monke.begit.di

import com.monke.begit.data.UserRepositoryImpl
import com.monke.begit.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
abstract class UserModule {

    @Binds
    abstract fun bindUserRepository(repositoryImpl: UserRepositoryImpl): UserRepository
}