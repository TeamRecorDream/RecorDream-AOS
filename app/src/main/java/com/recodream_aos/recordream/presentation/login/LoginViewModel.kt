package com.recodream_aos.recordream.presentation.login // ktlint-disable package-name

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.recodream_aos.recordream.data.remote.ServciePool
import com.recodream_aos.recordream.data.remote.api.LoginService

class LoginViewModel() : ViewModel() {
    private val loginService: LoginService = ServciePool.loginService
    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (token != null) {
            Log.d("kakaoLogin", "카카오계정 로그인 성공")
            Log.d("qwdqwdqwdqwdqwd", token.accessToken)
            //  getNewToken(token.accessToken)
        } else if (error != null) {
            Log.d("kakaoLogin", error.toString())
        }
    }

    //
    fun getKaKaoCallback(): (OAuthToken?, Throwable?) -> Unit {
        return kakaoLoginCallback
    }
//
//    private fun getNewToken(kakaoToken: String) {
//        Log.d("kakaoToken", kakaoToken)
//        viewModelScope.launch {
//            runCatching {
//                loginService.postLogin(
//                    RequestLogin(
//                        kakaoToken,
//                        "temp"
//                    )
//                )
//            }.onSuccess {
//                Log.d("eeeeeeeeeeeeeeeeeeeee", "${it.status}")
//            }.onFailure {
//                Log.d("eeeeeeeeeeeeeeeeeeeee", "$it")
//            }
//        }
//    }
}

// 인터셉트 진행
// 토큰관리
// 자동로그인하면 완료 !
