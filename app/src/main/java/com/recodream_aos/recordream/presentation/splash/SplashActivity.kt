package com.recodream_aos.recordream.presentation.splash // ktlint-disable package-name

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivitySplashBinding
import com.recodream_aos.recordream.presentation.login.LoginActivity

class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 김시하의 의뢰에 따라..
        // overridePendingTransition(R.anim.none, R.anim.horizon_exit)

        // 토큰 갱신 우선구현 후 자동로그인 구현
        initViewModel()
        handleSplash()
    }

    private fun initViewModel() {
        binding.viewModel = splashViewModel
        binding.lifecycleOwner = this
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
