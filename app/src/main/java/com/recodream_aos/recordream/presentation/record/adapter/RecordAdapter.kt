package com.recodream_aos.recordream.presentation.record.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.presentation.record.uistate.Emotion

class RecordAdapter : RecyclerView.Adapter<RecordViewHolder>() {
    private val emotionItems = listOf<Emotion>(
        Emotion(
            R.drawable.emotion_joy_selector,
            R.string.tv_record_joy,
        ),
        Emotion(
            R.drawable.emotion_sad_selector,
            R.string.tv_record_sad,
        ),
        Emotion(
            R.drawable.emotion_scary_selector,
            R.string.tv_record_scary,
        ),
        Emotion(
            R.drawable.emotion_strange_selector,
            R.string.tv_record_strange,
        ),
        Emotion(
            R.drawable.emotion_shy_selector,
            R.string.tv_record_shy,
        ),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(
            RecordViewHolder.getView(
                parent,
                LayoutInflater.from(parent.context),
            ),
        )
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(emotionItems[position])
    }

    override fun getItemCount(): Int = emotionItems.size
}
