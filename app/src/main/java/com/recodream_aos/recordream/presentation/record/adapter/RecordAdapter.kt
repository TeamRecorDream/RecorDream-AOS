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
    private var beforeSelectedIndex = INITIAL_VALUE
    private val emotionsUiState = mutableListOf(
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

    override fun getItemCount(): Int = emotionsUiState.size

    fun updateEmotionState(selectedEmotionIndex: Int) {
        if (selectedEmotionIndex == beforeSelectedIndex) {
            emotionsUiState[beforeSelectedIndex] = emotionsUiState[beforeSelectedIndex].copy(
                selected = !emotionsUiState[beforeSelectedIndex].selected,
            )
            notifyItemChanged(beforeSelectedIndex)
            beforeSelectedIndex = INITIAL_VALUE
            return
        }

        if (beforeSelectedIndex in RANGE) {
            emotionsUiState[beforeSelectedIndex].copy(selected = false).also {
                emotionsUiState[beforeSelectedIndex] = it
                notifyItemChanged(beforeSelectedIndex)
            }
        }

        emotionsUiState[selectedEmotionIndex].copy(selected = true).also {
            emotionsUiState[selectedEmotionIndex] = it
            notifyItemChanged(selectedEmotionIndex)
        }

        beforeSelectedIndex = selectedEmotionIndex

        // 아이템이 선택된 상태
        // 선택되지 않은 상태로 함수 분리
    }

    private fun EmotionUiState.updateItemChanged() {
        // 만들기
    }

    companion object {
        private const val INITIAL_VALUE = -1
        private val RANGE = 0..4
    }
}
