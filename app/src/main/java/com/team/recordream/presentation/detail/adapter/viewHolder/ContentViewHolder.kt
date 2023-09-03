package com.team.recordream.presentation.detail.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.databinding.ItemDetailContentBinding
import com.team.recordream.presentation.common.model.PlayButtonState
import com.team.recordream.presentation.detail.model.Content

class ContentViewHolder(
    private val binding: ItemDetailContentBinding,
    onClick: (PlayButtonState) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.onClick = { onClick }
    }

    fun bind(content: Content) {
        binding.content = content
    }

    companion object {
        fun from(parent: ViewGroup, onClick: (PlayButtonState) -> Unit): ContentViewHolder =
            ContentViewHolder(
                ItemDetailContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                onClick,
            )
    }
}
