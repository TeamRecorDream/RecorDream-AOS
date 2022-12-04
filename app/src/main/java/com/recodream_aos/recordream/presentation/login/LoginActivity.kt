package com.recodream_aos.recordream.presentation.login // ktlint-disable package-name

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityLoginBinding
import com.recodream_aos.recordream.presentation.MainActivity
import timber.log.Timber

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("*****HASHKEY*****").d(Utility.getKeyHash(this))

        initViewModel()
        initKakaoSdk()
        successKaKaoLogin()
        clickKakaoBtn()
    }

    private fun initViewModel() {
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this
    }

    private fun successKaKaoLogin() {
        if (loginViewModel.checkUserToken()) {
            val loginToMain = Intent(this, MainActivity::class.java)
            startActivity(loginToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(
            this,
            this.getString(R.string.appKey_init_kakao)
        )
    }

    private fun clickKakaoBtn() {
        binding.clLoginKakaobtn.setOnClickListener {
            kakaoLogin()
        }
    }

    private fun kakaoLogin() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error -> // TODO : 토큰 활용부분
            // TODO : 토큰 난독화처리하기

            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Log.d("*****kakaoLoginError*****", "접근이 거부 됨(동의 취소)")
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Log.d("*****kakaoLoginError*****", " 유효하지 않은 앱 ")
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Log.d("*****kakaoLoginError*****", "인증 수단이 유효하지 않아 인증할 수 없는 상태")
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Log.d("*****kakaoLoginError*****", "요청 파라미터 오류")
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Log.d("*****kakaoLoginError*****", "유효하지 않은 scope ID")
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Log.d("*****kakaoLoginError*****", "설정이 올바르지 않음(android key hash)")
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Log.d("*****kakaoLoginError*****", "서버 내부 에러")
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Log.d("*****kakaoLoginError*****", "앱이 요청 권한이 없음")
                    }
                    else -> { // Unknown
                        Log.d("*****kakaoLoginError*****", "기타 에러")
                    }
                }
            } else if (token != null) {
                // TODO: 최종적으로 카카오로그인 및 유저정보 가져온 결과

                UserApiClient.instance.me { user, error ->
                    Log.d("카카오계정으로 로그인 성공", "token: ${token.accessToken} \n\n + me : $user")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.d("카카오톡으로 로그인 실패", " $error")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.d("카카오톡으로 로그인 성공", token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }
}

/*
    private fun kakaoLogout() {
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                shortToast("로그아웃 실패. SDK에서 토큰 삭제됨: $error")
            } else {
                shortToast("로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    private fun kakaoUnlink() {
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                shortToast("연결 끊기 실패: $error")
            } else {
                shortToast("연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

*/
