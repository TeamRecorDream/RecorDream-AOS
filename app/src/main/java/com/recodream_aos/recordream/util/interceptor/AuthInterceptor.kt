package com.recodream_aos.recordream.util.interceptor // ktlint-disable package-name

//
// class AuthInterceptor @Inject constructor(
//    private val authRepositoryProvider: Provider<AuthRepository>
// ) : Interceptor {
//    private val authRepository: AuthRepository
//        get() = authRepositoryProvider.get()
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        // chain.request()는 사용자가 보낸 요청값을 가로챈 것
//        val originalRequest = chain.request()
//        val response = chain.proceed(originalRequest)
//
//        val temp = temp()
//        Log.d("나찍혀>", "찍햐나고 ㅋㅋ")
//        // auth/token 401일 때
//        when (response.code) {
//            401 -> {
//                val newRequest = temp?.let {
//                    originalRequest.newBuilder()
//                        .header("TOKEN", it)
//                }
//            }
//        }
//        return chain.proceed(originalRequest)
//    }
//
//    private fun temp() = runBlocking {
//        authRepository.getNewAccessToken()
//    }
// }
