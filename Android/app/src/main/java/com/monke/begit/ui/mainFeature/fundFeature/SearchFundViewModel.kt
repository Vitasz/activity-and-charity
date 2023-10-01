package com.monke.begit.ui.mainFeature.fundFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.begit.domain.model.Fund
import com.monke.begit.domain.repository.FundRepository
import com.monke.begit.domain.repository.SportRepository
import com.monke.begit.ui.mainFeature.activityFeature.SelectActivityViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFundViewModel(
    private val fundRepository: FundRepository
) : ViewModel() {

    private lateinit var allFunds: List<Fund>

    private val _funds = MutableStateFlow<List<Fund>>(ArrayList<Fund>())
    val funds = _funds.asStateFlow()

    init {
        viewModelScope.launch {
            val request = fundRepository.getFunds()
            if (request.isSuccess) {
                allFunds = request.getOrNull()!!
                _funds.value = allFunds
            }
        }
    }


    fun searchFund(query: String) {
        _funds.value = search(query)
    }

    private fun search(query: String) =
        allFunds.filter { it.name.lowercase().contains(query.lowercase()) }.sortedBy { it.name }



    class Factory @Inject constructor(
        private val fundRepository: FundRepository
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchFundViewModel(
                fundRepository = fundRepository
            ) as T
        }

    }
}