package com.recodream_aos.recordream.util.recorder // ktlint-disable package-name

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.recodream_aos.recordream.R

class RecordButton(
    context: Context,
    attributeSet: AttributeSet? = null
) : AppCompatImageButton(context, attributeSet) {

    init {
        setBackgroundResource(R.drawable.icn_mic_start)
    }

    fun updateIconWithState(state: RecordButtonState) {
        when (state) {
            RecordButtonState.BEFORE_RECORDING -> setImageResource(R.drawable.icn_mic_start)
            RecordButtonState.ON_RECORDING -> setImageResource(R.drawable.icn_mic_stop)
            RecordButtonState.AFTER_RECORDING -> setImageResource(R.drawable.icn_mic_reset)
        }
    }
}
