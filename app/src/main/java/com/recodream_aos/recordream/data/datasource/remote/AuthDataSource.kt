package com.recodream_aos.recordream.data.datasource.remote // ktlint-disable package-name

import com.recodream_aos.recordream.data.entity.remote.response.ResponseLogin

interface AuthDataSource {
    suspend fun postLogin(
        kakaoToken: String,
        fcmToken: String
    ): ResponseLogin
}
