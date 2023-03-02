package com.recodream_aos.recordream.data.datasource.remote // ktlint-disable package-name

import com.recodream_aos.recordream.data.api.AuthService
import com.recodream_aos.recordream.data.entity.remote.request.RequestLogin
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


    override suspend fun postToken(
        accessToken: String,
        refreshToken: String
    ): ResponseWrapper<ResponseNewToken> {
        return authService.postToken(accessToken, refreshToken)
    }
}
