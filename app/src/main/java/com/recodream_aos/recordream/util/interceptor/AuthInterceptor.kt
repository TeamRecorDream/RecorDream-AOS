package com.recodream_aos.recordream.util.interceptor // ktlint-disable package-name

import android.util.Log
import com.recodream_aos.recordream.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

// ktlint-disable package-name

class AuthInterceptor @Inject constructor(
    private val authRepositoryProvider: Provider<AuthRepository>,
) : Interceptor {
    private val authRepository: AuthRepository
        get() = authRepositoryProvider.get()

    override fun intercept(chain: Interceptor.Chain): Response {
        // chain.request()는 사용자가 보낸 모든 요청값을 가로챈 것
        // requestHeader도 포함되어있음
        // 서버통신 할때마다 가로챔 이새키가
        // 모든 서버통신을 다 들린다는 소리죠
        val originalRequest = chain.request()
        // url.encodedPath 메소드를 통해서
        // 모든 통신값의 url경로를 가져올 수 있다
        // url에 token이나 login이라는 단어가 포함되어있으면
        // 그냥 아무처리하지말고 originalRequest 그대로 갖고있고
        // 그 이외의 API리퀘스트라면
        // 헤더값에다가 토큰 다 넣어주라는 로직입니다
        val newRequest = if (containAuth(originalRequest.url.encodedPath)) {
            originalRequest
        } else {
            originalRequest
                .newBuilder()
                .addHeader(
                    "Authorization",
                    getAccessToken(),
                ).build()
        }
        val response = chain.proceed(newRequest)
        // 이렇게 리스폰스를 찍어보면 무슨 값이 날라오것쥬?
        Log.d("안녕안녕2", response.code.toString())
        // 만약에 만료코드406이고, 경로상에 token이나 login이란 단어가 없다면
        // 새로운 토큰으로 헤더를 교체해줍니당
        if ((response.code == 406) and !containAuth(newRequest.url.encodedPath)) {
            Log.d("안녕안녕3", "너 만료야!")
            val newAccessToken = getNewAccessToken()
            val requestWithNewToken = originalRequest.newBuilder()
                .addHeader("Authorization", newAccessToken)
                .build()
            response.close()
            return chain.proceed(requestWithNewToken)
            // 토큰만료 되었을 때, 분기처리
            // TODO : 다른API 붙이고 재확인 필요
        }

        return response
    }

    private fun getNewAccessToken() = runBlocking {
        authRepository.getNewAccessToken()
    }

    private fun getAccessToken() = runBlocking {
        authRepository.getAccessToken()
    }

    private fun containAuth(url: String): Boolean {
        if (url.contains(LOGIN) or url.contains(TOKEN)) return true
        return false
    }

    companion object {
        const val LOGIN = "/auth/login"
        const val TOKEN = "/auth/token"
    }
}
