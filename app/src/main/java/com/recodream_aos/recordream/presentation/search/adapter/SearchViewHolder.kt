package com.recodream_aos.recordream.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.databinding.ItemRecordFoundBinding
import com.recodream_aos.recordream.domain.model.SearchedRecord

class SearchViewHolder(
    private val binding: ItemRecordFoundBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private val tagAdapter: TagAdapter by lazy { TagAdapter() }

    init {
        binding.rvItemRecordTag.adapter = tagAdapter
    }

    fun bind(record: SearchedRecord) {
        binding.record = record
        // 태그어댑터
        // 배경색 설정하고 마무리
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemRecordFoundBinding =
            ItemRecordFoundBinding.inflate(layoutInflater, parent, false)
    }
}
