package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import com.recodream_aos.recordream.R

enum class Emotion(val emotionID: Int, val viewId: Int) {
    BLANK(0, R.id.cl_record_emotion),
    JOY(1, R.id.cl_record_joy),
    SAD(2, R.id.cl_record_sad),
    SCARY(3, R.id.cl_record_scary),
    STRANGE(4, R.id.cl_record_strange),
    SHY(5, R.id.cl_record_shy),
}
