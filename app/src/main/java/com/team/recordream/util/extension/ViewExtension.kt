package com.team.recordream.util

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.makeSnackBar(message: Int) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.anchorSnackBar(message: Int) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).apply {
        anchorView = this@anchorSnackBar
        this.view.setBackgroundColor(Color.parseColor("#373737"))
        setTextColor(Color.parseColor("#FFFFFF"))
    }.show()
}
