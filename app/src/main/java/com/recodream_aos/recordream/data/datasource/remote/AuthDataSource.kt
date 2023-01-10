package com.recodream_aos.recordream.data.datasource.remote // ktlint-disable package-name

import com.recodream_aos.recordream.data.entity.remote.response.ResponseLogin
import com.recodream_aos.recordream.data.entity.remote.response.ResponseNewToken
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper

interface AuthDataSource {
    suspend fun postLogin(
        kakaoToken: String,
        fcmToken: String
    ): ResponseLogin

    suspend fun tryLogin(
        kakaoToken: String?,
        fcmToken: String
    ): ResponseWrapper<ResponseLogin>

    suspend fun getNewToken(
        accessToken: String,
        refreshToken: String
    ): ResponseNewToken
}
