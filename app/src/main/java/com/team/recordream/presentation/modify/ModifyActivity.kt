package com.team.recordream.presentation.modify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.team.recordream.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ModifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
    }

    // 수정하기뷰 분리
}