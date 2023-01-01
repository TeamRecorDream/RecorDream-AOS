package com.recodream_aos.recordream.presentation.login // ktlint-disable package-name

import KakaoLoginManager
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    //  private val loginViewModel: LoginViewModel by viewModels()
    private val kakaoLoginManager = KakaoLoginManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("*****HASHKEY*****").d(Utility.getKeyHash(this))
        // initViewModel()
        clickLoginBtn()
    }
    // 이런식의 전달이 올바른 context 주입인가?
    // 뷰모델로 뺄 수 없을까?
    // 1. 토큰 서버 통신
    // interceptor 클래스 생성

    fun clickLoginBtn() {
        binding.clLoginKakaobtn.setOnClickListener {
            initKakaoLogin()
        }
    }

    fun initKakaoLogin() {
        lifecycleScope.launch {
            try {
                // 서비스 코드에서는 간단하게 로그인 요청하고 oAuthToken 을 받아올 수 있다.
                val oAuthToken = kakaoLoginManager.loginWithKakao()
                Log.d("MainActivity", "beanbean > $oAuthToken")
            } catch (error: Throwable) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    Log.d("MainActivity", "사용자가 명시적으로 취소")
                } else {
                    Log.e("MainActivity", "인증 에러 발생", error)
                }
            }
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
//
//    private fun initViewModel() {
//        //  binding.viewModel = loginViewModel
//        binding.lifecycleOwner = this
//    }
//
//    private fun kakaoLogin() {
//        // 카카오계정으로 로그인 공통 callback 구성
//        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
//        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
//            if (error != null) {
//                Timber.d("카카오계정으로 로그인 실패 : $error")
//                // setLogin(false)
//            } else if (token != null) {
//                // TODO: 최종적으로 카카오로그인 및 유저정보 가져온 결과
//                UserApiClient.instance.me { user, error ->
//                    Timber.d(
//                        "카카오계정으로 로그인 성공 \n\n " +
//                            "token: ${token.accessToken} \n\n " +
//                            "me: $user"
//                    )
//                    // setLogin(true)
//                }
//            }
//        }
//
//        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
//        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
//            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
//                Log.d("d", "ddddddddddddddddddddddddddddddddddd")
//                if (error != null) {
//                    Timber.d("카카오톡으로 로그인 실패 : $error")
//
//                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
//                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
//                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
//                        return@loginWithKakaoTalk
//                    }
// //
// //                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
//                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
//                } else if (token != null) {
//                    Timber.d("카카오톡으로 로그인 성공 ${token.accessToken}")
// //                    // setLogin(true)
//                }
//            }
//        } else {
//            //          UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
//        }
//    }
}
