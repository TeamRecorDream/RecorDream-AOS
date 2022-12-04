package com.recodream_aos.recordream.presentation.login

import androidx.lifecycle.ViewModel
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.AccessTokenInfo
import timber.log.Timber

class LoginViewModel : ViewModel() {

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
}
