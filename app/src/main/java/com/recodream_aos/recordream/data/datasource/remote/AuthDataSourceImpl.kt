package com.recodream_aos.recordream.data.datasource.remote // ktlint-disable package-name

import com.recodream_aos.recordream.data.api.LoginService
import com.recodream_aos.recordream.data.entity.remote.request.RequestLogin
import com.recodream_aos.recordream.data.entity.remote.response.ResponseLogin
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val loginService: LoginService
) : AuthDataSource {
    override suspend fun postLogin(kakaoToken: String, fcmToken: String): ResponseLogin {
        val responseLogin = loginService.postLogin(RequestLogin(kakaoToken, fcmToken))
        return requireNotNull(responseLogin.data)
    }
}
