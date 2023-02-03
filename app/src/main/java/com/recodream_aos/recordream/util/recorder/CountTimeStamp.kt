package com.recodream_aos.recordream.util.recorder // ktlint-disable package-name

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CountTimeStamp(
    context: Context,
    attributeSet: AttributeSet? = null
) : AppCompatTextView(context, attributeSet) {
    private var startTimeStamp = 0L

    private val countUpAction: Runnable = object : Runnable {
        override fun run() {
            // 시작했을 때 타임 트탬프를 계산
            val currentTimeStamp = SystemClock.elapsedRealtime()

            val countTimeSeconds =
                ((currentTimeStamp - startTimeStamp) / 1000L).toInt() // 얼마의 시간 차이가 나는지
            updateCountTime(countTimeSeconds)
            handler?.postDelayed(this, 1000L)
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
        updateCountTime(0)
    }

    private fun updateCountTime(countTimeSeconds: Int) {
        val minutes = countTimeSeconds / 60
        val seconds = countTimeSeconds % 60
        text = "%02d:%02d".format(minutes, seconds)
    }
}
