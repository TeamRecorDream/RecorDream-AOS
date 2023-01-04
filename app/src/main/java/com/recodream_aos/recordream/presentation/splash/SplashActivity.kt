package com.recodream_aos.recordream.presentation.splash // ktlint-disable package-name

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.presentation.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 김시하의 의뢰에 따라..
        // overridePendingTransition(R.anim.none, R.anim.horizon_exit)

        handleSplash()
    }

    private fun handleSplash() {
        val splashHandler = Handler(Looper.getMainLooper())
        val intentToLogin = Intent(this, LoginActivity::class.java)
        splashHandler.postDelayed(
            {
                intentToLogin.apply {
                    startActivity(intentToLogin)
                    finish()
                }
            },
            NINE_HUNDRED
        )
    }

    companion object {
        const val NINE_HUNDRED = 900L
    }
}
