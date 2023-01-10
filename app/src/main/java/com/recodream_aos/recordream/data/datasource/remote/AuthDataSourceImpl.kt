package com.recodream_aos.recordream.data.datasource.remote // ktlint-disable package-name

import com.recodream_aos.recordream.data.api.AuthService
import com.recodream_aos.recordream.data.entity.remote.request.RequestLogin
import com.recodream_aos.recordream.data.entity.remote.request.RequestNewToken
import com.recodream_aos.recordream.data.entity.remote.response.ResponseLogin
import com.recodream_aos.recordream.data.entity.remote.response.ResponseNewToken
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthDataSource {
    override suspend fun postLogin(kakaoToken: String, fcmToken: String): ResponseLogin {
        val responseLogin = authService.postLogin(RequestLogin(kakaoToken, fcmToken))
        return requireNotNull(responseLogin.data)
    }

    override suspend fun tryLogin(
        kakaoToken: String?,
        fcmToken: String
    ): ResponseWrapper<ResponseLogin> {
        return authService.postLogin(RequestLogin(kakaoToken, fcmToken))
    }

    override suspend fun getNewToken(accessToken: String, refreshToken: String): ResponseNewToken {
        val responseNewToken = authService.postNewToken(RequestNewToken(accessToken, refreshToken))
        return requireNotNull(responseNewToken.data)
    }
}
