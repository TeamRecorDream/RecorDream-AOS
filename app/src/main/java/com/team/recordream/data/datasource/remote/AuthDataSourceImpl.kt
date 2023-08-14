package com.team.recordream.data.datasource.remote // ktlint-disable package-name

import android.util.Log
import com.team.recordream.data.api.AuthService
import com.team.recordream.data.entity.remote.request.RequestLogin
import com.team.recordream.data.entity.remote.response.NoDataResponse
import com.team.recordream.data.entity.remote.response.ResponseLogin
import com.team.recordream.data.entity.remote.response.ResponseNewToken
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthDataSource {
    override suspend fun postLogin(kakaoToken: String, fcmToken: String): ResponseLogin {
        val responseLogin = authService.postLogin(RequestLogin(kakaoToken, fcmToken))
        return requireNotNull(responseLogin.data)
    }

    override suspend fun postToken(
        accessToken: String,
        refreshToken: String,
    ): ResponseWrapper<ResponseNewToken> {
        return authService.postToken(accessToken, refreshToken)
    }

    override suspend fun patchSignOut(): NoDataResponse {
        return authService.patchLogout()
    }

    override suspend fun deleteUser(): NoDataResponse {
        Log.d("AuthDataSourceImpl", "deleteUser: ${authService.deleteUser()}")
        return authService.deleteUser()
    }
}
