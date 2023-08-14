package com.team.recordream.data.datasource.remote // ktlint-disable package-name

import com.team.recordream.data.entity.remote.response.NoDataResponse
import com.team.recordream.data.entity.remote.response.ResponseLogin
import com.team.recordream.data.entity.remote.response.ResponseNewToken
import com.team.recordream.data.entity.remote.response.ResponseWrapper

interface AuthDataSource {
    suspend fun postLogin(
        kakaoToken: String,
        fcmToken: String,
    ): ResponseLogin

    suspend fun postToken(
        accessToken: String,
        refreshToken: String,
    ): ResponseWrapper<ResponseNewToken>

    suspend fun patchSignOut(): NoDataResponse

    suspend fun deleteUser(): NoDataResponse
}
