package com.recodream_aos.recordream.util.recorder // ktlint-disable package-name

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class TimeStampTextView(
    context: Context,
    attributeSet: AttributeSet? = null,
) : AppCompatTextView(context, attributeSet) {
    private var startTimeStamp = ZERO_LONG

    private val countUpAction: Runnable = object : Runnable {
        override fun run() {
            val currentTimeStamp = SystemClock.elapsedRealtime()
            val countTimeSeconds = ((currentTimeStamp - startTimeStamp) / ONE_SECOND).toInt()
            updateCountTime(countTimeSeconds)
            handler?.postDelayed(this, ONE_SECOND)
        }
    }

    fun startCountUp() {
        startTimeStamp = SystemClock.elapsedRealtime()
        handler?.post(countUpAction)
    }

    fun stopCountUp() {
        handler?.removeCallbacks(countUpAction)
    }

    fun clearCountTime() {
        updateCountTime(ZERO)
    }

    private fun updateCountTime(countTimeSeconds: Int) {
        val minutes = countTimeSeconds / ONE_MINUTE
        val seconds = countTimeSeconds % ONE_MINUTE
        text = TIME_FORMAT.format(minutes, seconds)
    }

    companion object {
        private const val ZERO = 0
        private const val ZERO_LONG = 0L
        private const val ONE_MINUTE = 60
        private const val ONE_SECOND = 1000L
        private const val TIME_FORMAT = "%02d:%02d"
    }
}
