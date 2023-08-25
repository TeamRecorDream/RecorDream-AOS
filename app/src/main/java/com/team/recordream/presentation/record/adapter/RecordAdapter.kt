package com.team.recordream.presentation.record.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.presentation.record.RecordClickListener
import com.team.recordream.presentation.record.uistate.Emotion.JOY
import com.team.recordream.presentation.record.uistate.Emotion.SAD
import com.team.recordream.presentation.record.uistate.Emotion.SCARY
import com.team.recordream.presentation.record.uistate.Emotion.SHY
import com.team.recordream.presentation.record.uistate.Emotion.STRANGE
import com.team.recordream.presentation.record.uistate.EmotionUiState

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

    fun updateEmotionState(selectedIndex: Int?) {
        if (selectedIndex == null) {
            emotionsUiState.replaceAll { it.copy(selected = false) }
            Log.d("123123- 이모션", emotionsUiState.toString())
            notifyDataSetChanged()
            return
        }
        updateIfSelectNew(selectedIndex - 1)

//        when (selectedIndex == beforeSelectedIndex) {
//            true -> updateIfSelectSame()
//            false -> updateIfSelectNew(selectedIndex)
//        }
//
        beforeSelectedIndex = selectedIndex - 1
    }

    private fun updateIfSelectSame() {
        emotionsUiState[beforeSelectedIndex].copy(selected = !emotionsUiState[beforeSelectedIndex].selected)
            .updateItemChanged(beforeSelectedIndex)
    }

    private fun updateIfSelectNew(selectedIndex: Int) {
        if (beforeSelectedIndex in RANGE) {
            emotionsUiState[beforeSelectedIndex].copy(selected = false)
                .updateItemChanged(beforeSelectedIndex)
        }
        emotionsUiState[selectedIndex].copy(selected = true)
            .updateItemChanged(selectedIndex)
    }

    private fun EmotionUiState.updateItemChanged(index: Int) {
        emotionsUiState[index] = this
        notifyItemChanged(index)
    }

    companion object {
        private const val INITIAL_VALUE = -1
        private val RANGE = 0..4
    }
}
