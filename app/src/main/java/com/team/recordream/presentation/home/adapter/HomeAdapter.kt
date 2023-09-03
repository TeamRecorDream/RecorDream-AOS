package com.team.recordream.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.team.recordream.databinding.HomeCardItemBinding
import com.team.recordream.presentation.home.model.UserRecords

class HomeAdapter(
    private val itemClick: (UserRecords) -> (Unit),
) : ListAdapter<UserRecords, PagerViewHolder>(diffResult) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding: HomeCardItemBinding = HomeCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return PagerViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffResult = object : DiffUtil.ItemCallback<UserRecords>() {
            override fun areItemsTheSame(oldItem: UserRecords, newItem: UserRecords): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserRecords, newItem: UserRecords): Boolean {
                return oldItem == newItem
            }
        }
    }
}
