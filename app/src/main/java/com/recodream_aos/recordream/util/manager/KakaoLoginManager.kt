package com.recodream_aos.recordream.util.manager // ktlint-disable package-name

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class KakaoLoginManager @Inject constructor(
    @ActivityContext val context: Context,
) {
    fun showKaKaoLogin(setCallback: (OAuthToken?, Throwable?) -> Unit) {
        when (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            true -> kakaoLoginAvailable(setCallback)
            false -> kakaoLoginUnavailable(setCallback)
        }
    }

    private fun kakaoLoginAvailable(setCallback: (OAuthToken?, Throwable?) -> Unit) {
        Log.d("*****KAKAOLOGIN/OK*****", "카카오톡으로 로그인 가능")
        UserApiClient.instance.loginWithKakaoTalk(
            context,

            callback = setCallback,
        )
    }

    private fun kakaoLoginUnavailable(setCallback: (OAuthToken?, Throwable?) -> Unit) {
        Log.d("*****KAKAOLOGIN/NO*****", "카카오톡으로 로그인 불가능")
        UserApiClient.instance.loginWithKakaoAccount(
            context,
            callback = setCallback,
        )
    }
}
