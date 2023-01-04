package com.recodream_aos.recordream.presentation.login // ktlint-disable package-name

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityLoginBinding
import com.recodream_aos.recordream.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("*****HASHKEY*****").d(Utility.getKeyHash(this))
        initViewModel()
        clickLoginBtn()
//        binding.ivLoginLogo.setOnClickListener {
//            kakaoUnlink()
//            // 토큰재확인용
//        }
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

    private fun initKaKaoLogin() = showKaKaoLogin(loginViewModel.getKaKaoCallback())

    private fun showKaKaoLogin(setCallback: (OAuthToken?, Throwable?) -> Unit) {
        when (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            true -> kakaoLoginAvailable(setCallback)
            false -> kakaoLoginUnavailable(setCallback)
        }
    }

    private fun kakaoLoginAvailable(setCallback: (OAuthToken?, Throwable?) -> Unit) {
        Log.d("*****KAKAOLOGIN/OK*****", "카카오톡으로 로그인 가능")
        UserApiClient.instance.loginWithKakaoTalk(
            this,
            callback = setCallback
        )
    }

    private fun kakaoLoginUnavailable(setCallback: (OAuthToken?, Throwable?) -> Unit) {
        Log.d("*****KAKAOLOGIN/NO*****", "카카오톡으로 로그인 불가능")
        UserApiClient.instance.loginWithKakaoAccount(
            this,
            callback = setCallback
        )
    }

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

    private fun kakaoUnlink() {
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.d("this", "연결 끊기 실패: $error")
            } else {
                Log.d("this", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }
}
