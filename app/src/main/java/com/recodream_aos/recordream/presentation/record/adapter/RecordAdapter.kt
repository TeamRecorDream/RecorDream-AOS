package com.recodream_aos.recordream.presentation.record.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.presentation.record.RecordClickListener
import com.recodream_aos.recordream.presentation.record.uistate.Emotion.JOY
import com.recodream_aos.recordream.presentation.record.uistate.Emotion.SAD
import com.recodream_aos.recordream.presentation.record.uistate.Emotion.SCARY
import com.recodream_aos.recordream.presentation.record.uistate.Emotion.SHY
import com.recodream_aos.recordream.presentation.record.uistate.Emotion.STRANGE
import com.recodream_aos.recordream.presentation.record.uistate.EmotionUiState

class RecordAdapter(
    private val onClick: RecordClickListener,
) : RecyclerView.Adapter<RecordViewHolder>() {
    private val emotionUiModelItems = listOf(JOY, SAD, SCARY, STRANGE, SHY)
    private val emotionsUiState = mutableListOf<EmotionUiState>(
        EmotionUiState(JOY, false),
        EmotionUiState(SAD, false),
        EmotionUiState(SCARY, false),
        EmotionUiState(STRANGE, false),
        EmotionUiState(SHY, false),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder =
        RecordViewHolder(
            onClick,
            RecordViewHolder.getView(parent, LayoutInflater.from(parent.context)),
        )

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(emotionsUiState[position])
    }

    override fun getItemCount(): Int = emotionUiModelItems.size

    fun updateEmotionState(selectedEmotionIndex: Int) {
        emotionsUiState[selectedEmotionIndex] =
            emotionsUiState[selectedEmotionIndex].copy(selected = !emotionsUiState[selectedEmotionIndex].selected)
        notifyItemChanged(selectedEmotionIndex)
    }
}
