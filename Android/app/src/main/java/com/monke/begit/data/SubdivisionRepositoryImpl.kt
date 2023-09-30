package com.monke.begit.data

import com.monke.begit.domain.mockedSubdivisions
import com.monke.begit.domain.model.Subdivision
import com.monke.begit.domain.repository.SubdivisionRepository
import javax.inject.Inject

class SubdivisionRepositoryImpl @Inject constructor(): SubdivisionRepository {

    private val subdivisionsList = ArrayList<Subdivision>()

    init {
        // TODO убрать это нахуй
        subdivisionsList.addAll(mockedSubdivisions)
    }

    override suspend fun createSubdivision(subdivision: Subdivision): Result<Subdivision> {
        val newSubdivision = subdivision.copy(id = subdivisionsList.maxBy { it.id }.id + 1)
        subdivisionsList.add(newSubdivision)
        return Result.success(newSubdivision)
    }

    override suspend fun getSubdivisionByCode(code: String): Result<Subdivision?> {
        return Result.success(subdivisionsList.find { it.code == code  })
    }


}