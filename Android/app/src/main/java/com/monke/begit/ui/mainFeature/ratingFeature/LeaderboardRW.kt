package com.monke.begit.ui.mainFeature.ratingFeature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monke.begit.databinding.ItemActivityBinding
import com.monke.begit.domain.model.SportActivity
import com.monke.begit.ui.uiModels.LeaderboardUser

class LeaderboardRW(private var users: List<LeaderboardUser>) :
    RecyclerView.Adapter<LeaderboardRW.LeaderboardViewHolder>() {


    class LeaderboardViewHolder(
        private val binding: ItemActivityBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(user: LeaderboardUser) {
            binding.txtActivity.text = user.name
            binding.txtMoney.text = "${user.moneyEarned}₽"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val binding = ItemActivityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return LeaderboardViewHolder(binding)
    }

    fun setItems(users: List<LeaderboardUser>) {
        this.users = users
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = users.count()

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        holder.bind(users[position])
    }
}