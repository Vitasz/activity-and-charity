package com.monke.begit.ui.mainFeature.activityFeature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monke.begit.R
import com.monke.begit.data.remote.PhysicalActivity
import com.monke.begit.databinding.ItemActivityBinding
import com.monke.begit.domain.model.SportActivity
import com.monke.begit.ui.uiModels.LeaderboardUser

class SportActivityRW(
    private var items: List<SportActivity>
) : RecyclerView.Adapter<SportActivityRW.SportActivityViewHolder>() {


    class SportActivityViewHolder(
        private val binding: ItemActivityBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(sportActivity: SportActivity) {
            binding.txtActivity.text = sportActivity.name
            binding.txtMoney.text = "${sportActivity.moneyEarned}₽"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportActivityViewHolder {
        val binding = ItemActivityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return SportActivityViewHolder(binding)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: SportActivityViewHolder, position: Int) {
        holder.bind(items[position])
    }
    fun setItems(items: List<SportActivity>) {
        this.items = items
        notifyDataSetChanged()
    }
}