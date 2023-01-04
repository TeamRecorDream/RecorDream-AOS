package com.recodream_aos.recordream.presentation.login // ktlint-disable package-name

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.recodream_aos.recordream.data.remote.ServciePool
import com.recodream_aos.recordream.data.remote.api.LoginService
import com.recodream_aos.recordream.data.remote.request.RequestLogin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    private val _signUpSuccess = MutableStateFlow(false)
    val signUpSuccess: StateFlow<Boolean> get() = _signUpSuccess
    private val loginService: LoginService = ServciePool.loginService
    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (token != null) {
            Log.d("*****KAKAOLOGIN/SUCCESS*****", "카카오계정 로그인 성공")
            getNewToken(token.accessToken)
        } else if (error != null) {
            Log.d("*****KAKAOLOGIN/FAILURE*****", "$error")
        }
    }

    fun getKaKaoCallback(): (OAuthToken?, Throwable?) -> Unit {
        return kakaoLoginCallback
    }

    private fun isSignUpSuccess() {
        _signUpSuccess.value = true
    }

    private fun isSignUpFailure() {
        _signUpSuccess.value = false
    }

    private fun getNewToken(kakaoToken: String) {
        viewModelScope.launch {
            runCatching {
                loginService.postLogin(
                    RequestLogin(
                        kakaoToken,
                        "temp"
                    )
                )
            }.onSuccess {
                isSignUpSuccess()
            }.onFailure {
                isSignUpFailure()
            }
        }
    }
}

// 인터셉트 진행
// 토큰관리
// 자동로그인하면 완료 !
