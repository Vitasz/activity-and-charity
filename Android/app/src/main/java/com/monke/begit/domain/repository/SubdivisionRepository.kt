package com.monke.begit.domain.repository

import com.monke.begit.domain.model.Subdivision

interface SubdivisionRepository {

    suspend fun createSubdivision(subdivision: Subdivision): Result<Subdivision>

    suspend fun getSubdivisionByCode(code: String): Result<Subdivision?>

}