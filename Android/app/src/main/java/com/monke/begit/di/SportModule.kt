package com.monke.begit.di

import com.monke.begit.data.SportRepositoryImpl
import com.monke.begit.domain.repository.SportRepository
import dagger.Binds
import dagger.Module

@Module
abstract class SportModule {

    @Binds
    abstract fun bindsSportRepository(repositoryImpl: SportRepositoryImpl): SportRepository

}