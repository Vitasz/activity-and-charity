package com.monke.begit.data

import com.monke.begit.domain.mockedFunds
import com.monke.begit.domain.model.Fund
import com.monke.begit.domain.repository.FundRepository
import javax.inject.Inject

class FundRepositoryImpl @Inject constructor(): FundRepository {


    override suspend fun getFunds(): Result<List<Fund>> {
        return Result.success(mockedFunds)
    }

}