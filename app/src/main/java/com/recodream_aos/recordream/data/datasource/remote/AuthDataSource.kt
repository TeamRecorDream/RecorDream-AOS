package com.recodream_aos.recordream.data.datasource.remote // ktlint-disable package-name

import com.recodream_aos.recordream.data.entity.remote.response.NoDataResponse
import com.recodream_aos.recordream.data.entity.remote.response.ResponseLogin
import com.recodream_aos.recordream.data.entity.remote.response.ResponseNewToken
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper

interface AuthDataSource {
    suspend fun postLogin(
        kakaoToken: String,
        fcmToken: String
    ): ResponseLogin

    suspend fun postToken(
        accessToken: String,
        refreshToken: String
    ): ResponseWrapper<ResponseNewToken>

    suspend fun patchSignOut(): NoDataResponse

    suspend fun deleteUser(): NoDataResponse
}
