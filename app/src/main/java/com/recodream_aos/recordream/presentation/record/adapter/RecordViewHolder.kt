package com.recodream_aos.recordream.presentation.record.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.databinding.ItemListEmotionBinding
import com.recodream_aos.recordream.presentation.record.uistate.Emotion

class RecordViewHolder(
    private val binding: ItemListEmotionBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(emotion: Emotion) {
        binding.emotion = emotion
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemListEmotionBinding =
            ItemListEmotionBinding.inflate(layoutInflater, parent, false)
    }
}
