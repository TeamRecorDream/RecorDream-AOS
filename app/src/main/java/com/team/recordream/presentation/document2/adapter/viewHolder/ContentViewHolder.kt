package com.team.recordream.presentation.document2.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.databinding.ItemDocumentContentBinding
import com.team.recordream.presentation.document2.model.ContentUiModel

class ContentViewHolder(
    private val binding: ItemDocumentContentBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(content: ContentUiModel) {
        binding.content = content
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemDocumentContentBinding =
            ItemDocumentContentBinding.inflate(layoutInflater, parent, false)
    }
}
