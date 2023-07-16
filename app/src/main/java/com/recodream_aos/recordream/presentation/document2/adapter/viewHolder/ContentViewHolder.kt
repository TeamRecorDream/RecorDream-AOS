package com.recodream_aos.recordream.presentation.document2.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.databinding.ItemDocumentContentBinding
import com.recodream_aos.recordream.presentation.document2.model.DocumentUiModel

class ContentViewHolder(
    private val binding: ItemDocumentContentBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DocumentUiModel) {
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemDocumentContentBinding =
            ItemDocumentContentBinding.inflate(layoutInflater, parent, false)
    }
}
