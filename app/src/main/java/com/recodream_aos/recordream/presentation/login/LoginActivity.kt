package com.recodream_aos.recordream.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.common.util.Utility
import com.recodream_aos.recordream.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val keyHash = Utility.getKeyHash(this)
        Log.d("*****HASHKEY*****", "$keyHash")
    }
}
