package com.team.recordream.presentation.record.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.databinding.ItemListEmotionBinding
import com.team.recordream.presentation.record.model.EmotionState
import com.team.recordream.presentation.record.uistate.Emotion
import com.team.recordream.presentation.record.uistate.Emotion.*

class RecordViewHolder(
    onClick: (emotionId: Int) -> Unit,
    private val binding: ItemListEmotionBinding,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { onClick(absoluteAdapterPosition) }
    }

    fun bind(emotion: EmotionState) {
        Log.d("12312399", emotion.selected.toString())
        binding.emotion = emotion
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemListEmotionBinding =
            ItemListEmotionBinding.inflate(layoutInflater, parent, false)

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
