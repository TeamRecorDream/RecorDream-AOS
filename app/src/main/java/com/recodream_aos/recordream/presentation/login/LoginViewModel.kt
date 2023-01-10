package com.recodream_aos.recordream.presentation.login // ktlint-disable package-name

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.recodream_aos.recordream.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _signUpSuccess = MutableStateFlow(false)
    val signUpSuccess: StateFlow<Boolean> get() = _signUpSuccess
    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (token != null) {
            Log.d("*****KAKAOLOGIN/SUCCESS*****", "카카오계정 로그인 성공")
            getUserToken(token.accessToken)
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

    private fun getUserToken(kakaoToken: String) {
        viewModelScope.launch {
            if (authRepository.postLogin(
                    kakaoToken,
                    "temp"
                )
            ) isSignUpSuccess() else isSignUpFailure()
            // 트라이-캐치 분기처리
        }
    }
}

// 인터셉트 진행
// 토큰관리
// 자동로그인하면 완료 !
