package com.recodream_aos.recordream.domain.repository // ktlint-disable package-name

interface AuthRepository {
    suspend fun postLogin(
        kakaoToken: String,
        fcmToken: String
    ): Boolean

    suspend fun postToken(): Boolean

    suspend fun getNewAccessToken(): String

    suspend fun getAccessToken(): String
}
