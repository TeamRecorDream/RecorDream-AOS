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
    // 1. 힐트 주입받은 외부클래스는 코루틴적용이 안되는가
    // 2. 뷰모델로 연결, 뷰는 뷰모델로 컨텍스트 전달
    // 3. 뷰모델로 연결하는 것은 안좋은가? 쉐어드 프리퍼런스처럼
    // 4. ㅇㅇ

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
