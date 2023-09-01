package com.team.recordream.presentation.detail.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.databinding.ItemDetailContentBinding
import com.team.recordream.presentation.detail.model.Content

class ContentViewHolder(
    private val binding: ItemDetailContentBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(content: Content) {
        binding.content = content
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemDetailContentBinding =
            ItemDetailContentBinding.inflate(layoutInflater, parent, false)
    }
}
