package com.recodream_aos.recordream.presentation // ktlint-disable package-name

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.recodream_aos.recordream.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
