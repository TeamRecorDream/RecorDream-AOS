package com.recodream_aos.recordream.data.repository // ktlint-disable package-name

import android.util.Log
import com.recodream_aos.recordream.data.datasource.local.SharedPreferenceDataSource
import com.recodream_aos.recordream.data.datasource.remote.AuthDataSource
import com.recodream_aos.recordream.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : AuthRepository {
    override suspend fun postLogin(kakaoToken: String, fcmToken: String): Boolean {
        return try {
            val tokens = authDataSource.postLogin(kakaoToken, fcmToken) ?: return false
            saveTokens(tokens.accessToken, tokens.refreshToken)
            Log.d("LOGIN_SUCCESS", tokens.accessToken)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPreferenceDataSource.setAccessToken(accessToken)
        sharedPreferenceDataSource.setRefreshToken(refreshToken)
    }
}
