package com.recodream_aos.recordream.presentation.record.uistate

import com.recodream_aos.recordream.R

enum class Emotion(val emotionName: Int, val viewId: Int) {
    JOY(R.string.tv_record_joy, R.drawable.emotion_joy_selector),
    SAD(R.string.tv_record_sad, R.drawable.emotion_sad_selector),
    SCARY(R.string.tv_record_scary, R.drawable.emotion_scary_selector),
    STRANGE(R.string.tv_record_strange, R.drawable.emotion_strange_selector),
    SHY(R.string.tv_record_shy, R.drawable.emotion_shy_selector),
}
