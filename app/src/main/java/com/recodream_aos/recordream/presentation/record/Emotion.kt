package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import com.recodream_aos.recordream.R

// ktlint-disable package-name

enum class Emotion(val emotionID: Int, val viewId: Int) {
    JOY(1, R.id.iv_record_joy),
    SAD(2, R.id.iv_record_sad),
    SCARY(3, R.id.iv_record_scary),
    STRANGE(4, R.id.iv_record_strange),
    SHY(5, R.id.iv_record_shy),
}
