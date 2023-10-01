package com.monke.begit.domain.repository

import com.monke.begit.domain.model.Fund

interface FundRepository {


    suspend fun getFunds(): Result<List<Fund>>

}