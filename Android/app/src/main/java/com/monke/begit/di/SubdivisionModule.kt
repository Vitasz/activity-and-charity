package com.monke.begit.di

import com.monke.begit.data.SubdivisionRepositoryImpl
import com.monke.begit.domain.repository.SubdivisionRepository
import dagger.Binds
import dagger.Module

@Module
abstract class SubdivisionModule {

    @Binds
    abstract fun bindSubdivisionRepository(repositoryImpl: SubdivisionRepositoryImpl): SubdivisionRepository
}