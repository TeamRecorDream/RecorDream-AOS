package com.recodream_aos.recordream.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.databinding.ItemTagBinding
import com.recodream_aos.recordream.presentation.record.uistate.Genre

class TagViewHolder(
    private val binding: ItemTagBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(tag: Int) {
        binding.genre = Genre.getValue(tag)
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemTagBinding =
            ItemTagBinding.inflate(layoutInflater, parent, false)
    }
}
