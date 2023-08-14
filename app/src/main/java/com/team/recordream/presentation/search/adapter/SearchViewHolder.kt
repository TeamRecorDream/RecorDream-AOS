package com.team.recordream.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.databinding.ItemRecordFoundBinding
import com.team.recordream.presentation.search.uistate.SearchedRecordUiState

class SearchViewHolder(
    private val onClick: (String) -> Unit,
    private val binding: ItemRecordFoundBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private val tagAdapter: TagAdapter by lazy { TagAdapter() }

    init {
        binding.rvItemRecordTag.adapter = tagAdapter
        binding.rvItemRecordTag.setHasFixedSize(true)
    }

    fun bind(record: SearchedRecordUiState) {
        binding.root.setOnClickListener { onClick(record._id) }
        binding.record = record
        tagAdapter.updateTags(record.genre)
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemRecordFoundBinding =
            ItemRecordFoundBinding.inflate(layoutInflater, parent, false)
    }
}
