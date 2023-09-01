package com.team.recordream.presentation.record.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.databinding.ItemListEmotionBinding
import com.team.recordream.presentation.record.model.Emotion
import com.team.recordream.presentation.record.model.Emotion.*
import com.team.recordream.presentation.record.model.EmotionState

class RecordViewHolder(
    private val binding: ItemListEmotionBinding,
    onClick: (emotionId: Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { onClick(absoluteAdapterPosition) }
    }

    fun bind(emotion: EmotionState) {
        binding.emotion = emotion
    }

    companion object {
        fun from(parent: ViewGroup, onClick: (emotionId: Int) -> Unit): RecordViewHolder =
            RecordViewHolder(
                ItemListEmotionBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onClick,
            )

        @JvmStatic
        @BindingAdapter("emotionText")
        fun setEmotionText(textView: TextView, emotionId: Int) {
            when (Emotion.getValue(emotionId)) {
                JOY -> textView.setText(JOY.emotionName)
                SAD -> textView.setText(SAD.emotionName)
                SCARY -> textView.setText(SCARY.emotionName)
                STRANGE -> textView.setText(STRANGE.emotionName)
                SHY -> textView.setText(SHY.emotionName)
                else -> throw IllegalArgumentException("[ERROR] INVALID EMOTION ID")
            }
        }

        @JvmStatic
        @BindingAdapter("emotionSrc")
        fun setEmotionImage(view: View, emotionId: Int) {
            when (Emotion.getValue(emotionId)) {
                JOY -> view.setBackgroundResource(JOY.viewId)
                SAD -> view.setBackgroundResource(SAD.viewId)
                SCARY -> view.setBackgroundResource(SCARY.viewId)
                STRANGE -> view.setBackgroundResource(STRANGE.viewId)
                SHY -> view.setBackgroundResource(SHY.viewId)
                else -> throw IllegalArgumentException("[ERROR] INVALID EMOTION ID")
            }
        }
    }
}
