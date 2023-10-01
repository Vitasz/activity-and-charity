package com.monke.begit.ui.mainFeature.fundFeature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monke.begit.databinding.ItemActivityBinding
import com.monke.begit.databinding.ItemFundBinding
import com.monke.begit.domain.model.Fund
import com.monke.begit.ui.uiModels.LeaderboardUser

class FundRW(private var funds: List<Fund>) :
    RecyclerView.Adapter<FundRW.FundViewHolder>() {


    class FundViewHolder(
        private val binding: ItemFundBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(fund: Fund) {
            binding.txtFund.text = fund.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundViewHolder {
        val binding = ItemFundBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return FundViewHolder(binding)
    }

    fun setItems(funds: List<Fund>) {
        this.funds = funds
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = funds.count()

    override fun onBindViewHolder(holder: FundViewHolder, position: Int) {
        holder.bind(funds[position])
    }

}