//package com.recodream_aos.recordream.util.interceptor // ktlint-disable package-name
//
//import android.util.Log
//import com.recodream_aos.recordream.domain.repository.AuthRepository
//import kotlinx.coroutines.runBlocking
//import okhttp3.Interceptor
//import okhttp3.Response
//import javax.inject.Inject
//import javax.inject.Provider
//
//// ktlint-disable package-name
//
//class AuthInterceptor @Inject constructor(
//    private val authRepositoryProvider: Provider<AuthRepository>
//) : Interceptor {
//    private val authRepository: AuthRepository
//        get() = authRepositoryProvider.get()
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        // chain.request()는 사용자가 보낸 모든 요청값을 가로챈 것
//        // requestHeader도 포함되어있음
//        // 서버통신 할때마다 가로챔 이새키가
//        // 모든 서버통신을 다 들린다는 소리죠
//        Log.d("흐엉", "흐어엉3")
//        val originalRequest = chain.request()
//        Log.d("흐엉", "흐어엉3")
//        // url.encodedPath 메소드를 통해서
//        // 모든 통신값의 url경로를 가져올 수 있다
//        // 이 url로 특정API를 인터셉터 제외시킬 수 있다!
//        val url = originalRequest.url.encodedPath
//        val newRequest = if (url.contains(LOGIN) or url.contains(TOKEN)) {
//            originalRequest.newBuilder().addHeader("Authorization", getAccessToken()).build()
//        } else originalRequest
//
//        val response = chain.proceed(newRequest)
////        when (response.code) {
////            401 -> {
////                Log.d("NOOO", "NOO")
////                // 스플래시에서 카카오토큰을 보내주면 안됨
////            }
////        }
//
//        return response
//    }
//
//    private fun getAccessToken() = runBlocking {
//        authRepository.getAccessToken()
//    }
//
//    companion object {
//        const val LOGIN = "/auth/login"
//        const val TOKEN = "/auth/token"
//    }
//}
