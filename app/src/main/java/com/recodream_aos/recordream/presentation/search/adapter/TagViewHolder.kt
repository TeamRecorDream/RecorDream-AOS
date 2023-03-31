package com.recodream_aos.recordream.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.databinding.ItemTagBinding

class TagViewHolder(
    private val binding: ItemTagBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(i: String) {
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemTagBinding =
            ItemTagBinding.inflate(layoutInflater, parent, false)
    }
}
