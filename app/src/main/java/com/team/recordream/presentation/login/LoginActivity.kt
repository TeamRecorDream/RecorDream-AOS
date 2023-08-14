package com.team.recordream.presentation.login // ktlint-disable package-name

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.common.util.Utility
import com.team.recordream.R
import com.team.recordream.base.BindingActivity
import com.team.recordream.databinding.ActivityLoginBinding
import com.team.recordream.presentation.MainActivity
import com.team.recordream.util.manager.KakaoLoginManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var kakaoLoginManager: KakaoLoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("*****HASHKEY*****").d(Utility.getKeyHash(this))
        initViewModel()
        clickLoginBtn()
    }

    private fun initViewModel() {
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this
    }

    private fun clickLoginBtn() {
        binding.clLoginKakaobtn.setOnClickListener {
            initKaKaoLogin()
            collectSignUpResult()
        }
    }

    private fun initKaKaoLogin() =
        kakaoLoginManager.showKaKaoLogin(loginViewModel.getKaKaoCallback())

    private fun collectSignUpResult() {
        lifecycleScope.launchWhenStarted {
            loginViewModel.signUpSuccess.collectLatest {
                if (it) startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        val intentToMain = Intent(this, MainActivity::class.java)
        startActivity(intentToMain)
        finish()
    }
}
