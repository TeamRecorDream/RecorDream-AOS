package com.recodream_aos.recordream.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.AccessTokenInfo
import com.recodream_aos.recordream.data.remote.ServciePool
import com.recodream_aos.recordream.data.remote.api.LoginService
import com.recodream_aos.recordream.data.remote.request.RequestLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLogin: KakaoLogin
) : ViewModel() {
    private val loginService: LoginService = ServciePool.loginService

    fun checkUserToken(): Boolean {
        var validUserToken = false
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (checkErrorBoolean(error) and checkTokenInfoBoolean(tokenInfo)) {
                validUserToken = true
            }
        }
        return validUserToken
    }

    private fun checkErrorBoolean(error: Throwable?): Boolean {
        if (error != null) {
            Timber.tag("토큰 정보 보기 실패").d(error)
            return false
        }
        return true
    }

    private fun checkTokenInfoBoolean(tokenInfo: AccessTokenInfo?): Boolean {
        if (tokenInfo != null) {
            Timber.d("토큰 정보 보기 성공")
            return true
        }
        return false
    }

    fun initNetwork() {
        viewModelScope.launch {
            runCatching {
                loginService.postLogin(
                    RequestLogin(
                        "dd",
                        "dd"
                    )
                )
            }.onSuccess {
                Log.d("qwdqdqdwqddwddqddqwd","qwdqdwdqwdqwdqwdqwdqd")
                Timber.d("success")
            }.onFailure {
                Log.d("qwdqdqdwqddwddqddqwd", it.toString())
                Timber.d(it)
            }
        }
    }
}
