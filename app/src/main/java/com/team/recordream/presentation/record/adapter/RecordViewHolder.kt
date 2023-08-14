package com.team.recordream.presentation.record.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.databinding.ItemListEmotionBinding
import com.team.recordream.presentation.record.RecordClickListener
import com.team.recordream.presentation.record.uistate.EmotionUiState

class RecordViewHolder(
    onClick: RecordClickListener,
    private val binding: ItemListEmotionBinding,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.onClick = onClick
    }

    fun bind(emotionUiState: EmotionUiState) {
        binding.emotion = emotionUiState
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemListEmotionBinding =
            ItemListEmotionBinding.inflate(layoutInflater, parent, false)
    }
}
