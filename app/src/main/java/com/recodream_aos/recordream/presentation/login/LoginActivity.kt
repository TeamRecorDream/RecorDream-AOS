package com.recodream_aos.recordream.presentation.login // ktlint-disable package-name

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("*****HASHKEY*****").d(Utility.getKeyHash(this))
        initViewModel()
        loginViewModel.getKaKaoCallback()
        clickLoginBtn()
    }

    private fun initViewModel() {
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this
    }

    private fun clickLoginBtn() {
        binding.clLoginKakaobtn.setOnClickListener {
            initKaKaoLogin()
        }
    }

    private fun initKaKaoLogin() = showKaKaoLogin(loginViewModel.getKaKaoCallback())

    private fun showKaKaoLogin(setCallback: (OAuthToken?, Throwable?) -> Unit) {
        val isKaKaoLoginAvailable = UserApiClient.instance.isKakaoTalkLoginAvailable(this)
        if (isKaKaoLoginAvailable) {
            Log.d("kakaoLogin", "카카오톡으로 로그인 가능")
            UserApiClient.instance.loginWithKakaoTalk(
                this,
                callback = setCallback
            )
        } else {
            Log.d("kakaoLogin", "카카오톡으로 로그인 불가능")
            UserApiClient.instance.loginWithKakaoAccount(
                this,
                callback = setCallback
            )
        }
    }

//    private fun kakaoUnlink() {
//        // 연결 끊기
//        UserApiClient.instance.unlink { error ->
//            if (error != null) {
//                Log.d("this", "연결 끊기 실패: $error")
//            } else {
//                Log.d("this", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
//                // setLogin(false)
//            }
//        }
//    }
}
