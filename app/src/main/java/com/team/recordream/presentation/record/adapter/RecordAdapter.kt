package com.team.recordream.presentation.record.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.team.recordream.presentation.record.model.EmotionState

class RecordAdapter(
    private val onClick: (emotionId: Int) -> Unit,
) : ListAdapter<EmotionState, RecordViewHolder>(diffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder =
        RecordViewHolder.from(parent, onClick)

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<EmotionState>() {
            override fun areItemsTheSame(oldItem: EmotionState, newItem: EmotionState): Boolean {
                return oldItem.selectedEmotion == newItem.selectedEmotion
            }

            override fun areContentsTheSame(oldItem: EmotionState, newItem: EmotionState): Boolean {
                return oldItem == newItem
            }

        }
    }

}
