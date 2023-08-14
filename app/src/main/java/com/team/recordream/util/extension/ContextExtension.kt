package com.team.recordream.util

import android.content.Context
import android.widget.Toast
import kotlin.math.roundToInt

fun Context.shortToastByString(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.shortToastByInt(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.dpToPixel(dp: Int): Int =
    (dp * resources.displayMetrics.density).roundToInt()
