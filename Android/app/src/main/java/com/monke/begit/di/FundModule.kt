package com.monke.begit.di

import com.monke.begit.data.FundRepositoryImpl
import com.monke.begit.data.SportRepositoryImpl
import com.monke.begit.domain.repository.FundRepository
import com.monke.begit.domain.repository.SportRepository
import dagger.Binds
import dagger.Module

@Module
abstract class FundModule {

    @Binds
    abstract fun bindsFundRepository(repositoryImpl: FundRepositoryImpl): FundRepository

}