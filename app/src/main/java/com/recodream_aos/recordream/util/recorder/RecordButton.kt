package com.recodream_aos.recordream.util.recorder // ktlint-disable package-name

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.util.recorder.RecordButtonState.AFTER_RECORDING
import com.recodream_aos.recordream.util.recorder.RecordButtonState.BEFORE_RECORDING
import com.recodream_aos.recordream.util.recorder.RecordButtonState.ON_RECORDING

class RecordButton(
    context: Context,
    attributeSet: AttributeSet? = null
) : AppCompatImageButton(context, attributeSet) {

    fun updateIconWithState(state: RecordButtonState) {
        when (state) {
            BEFORE_RECORDING -> setBackgroundResource(R.drawable.icn_mic_start)
            ON_RECORDING -> setBackgroundResource(R.drawable.icn_mic_stop)
            AFTER_RECORDING -> setBackgroundResource(R.drawable.icn_mic_reset)
        }
    }
}
