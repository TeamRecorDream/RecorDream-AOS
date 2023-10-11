package com.team.recordream.domain.repository

import com.team.recordream.data.entity.remote.response.NoDataResponse

// ktlint-disable package-name

interface AuthRepository {
    suspend fun postLogin(
        kakaoToken: String,
        fcmToken: String,
    ): Boolean

    suspend fun postToken(): Boolean

    suspend fun getNewAccessToken(): String

    suspend fun getAccessToken(): String

    fun unLinkKakaoAccount(initSuccessWithdraw: (Boolean) -> Unit)
    fun logoutKakaoAccount(logout: (Boolean) -> Unit)

    suspend fun getFcmToken(getFcmToken: (String) -> Unit)

    suspend fun patchSignOut(): Result<NoDataResponse>

    suspend fun deleteUser(): Result<NoDataResponse>
}
