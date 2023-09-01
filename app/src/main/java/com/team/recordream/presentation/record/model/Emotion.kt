package com.team.recordream.presentation.record.model

import com.team.recordream.R

enum class Emotion(val emotionName: Int, val viewId: Int) {
    JOY(R.string.tv_record_joy, R.drawable.emotion_joy_selector),
    SAD(R.string.tv_record_sad, R.drawable.emotion_sad_selector),
    SCARY(R.string.tv_record_scary, R.drawable.emotion_scary_selector),
    STRANGE(R.string.tv_record_strange, R.drawable.emotion_strange_selector),
    SHY(R.string.tv_record_shy, R.drawable.emotion_shy_selector),
    ALL(R.string.tv_record_all, R.drawable.emotion_blank_selector),
    ;

    companion object {
        private const val CORRECTION_VALUE = 1


        fun getValue(emotionId: Int): Emotion = Emotion.values().find { emotion ->
            emotion.ordinal + CORRECTION_VALUE == emotionId
        } ?: ALL
    }
}
