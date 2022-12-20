package com.recodream_aos.recordream.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.recodream_aos.recordream.util.extension.loginWithKakao
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import javax.inject.Inject

class KakaoLogin @Inject constructor(@ActivityContext context: Context) {
    fun initServer() {

        lifecycleScope.launch {
            try {
                // 서비스 코드에서는 간단하게 로그인 요청하고 oAuthToken 을 받아올 수 있다.
                val oAuthToken = UserApiClient.loginWithKakao(context)
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
}
