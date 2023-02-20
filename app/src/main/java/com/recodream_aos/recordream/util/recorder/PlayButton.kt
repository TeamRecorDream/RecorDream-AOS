package com.recodream_aos.recordream.util.recorder // ktlint-disable package-name

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.util.recorder.PlayButtonState.RECORDER_PLAY
import com.recodream_aos.recordream.util.recorder.PlayButtonState.RECORDER_STOP

class PlayButton(
    context: Context,
    attributeSet: AttributeSet? = null
) : AppCompatImageButton(context, attributeSet) {

    init {
        setBackgroundResource(R.drawable.icn_start)
    }

    fun updateIconWithState(state: PlayButtonState) {
        when (state) {
            RECORDER_PLAY -> setBackgroundResource(R.drawable.icn_start)
            RECORDER_STOP -> setBackgroundResource(R.drawable.icn_stop)
        }
    }
}
