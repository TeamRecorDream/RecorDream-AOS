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
            val tokens = authDataSource.postLogin(kakaoToken, fcmToken)
            saveTokens(tokens.accessToken, tokens.refreshToken)
            saveKakaoToken(kakaoToken)
            true
        } catch (e: Exception) {
            Log.d("LOGIN_FAILURE", e.toString())
            false
        }
    }

    override suspend fun tryLogin(): Boolean {
        val localKakaoToken = sharedPreferenceDataSource.getKakaoToken()
        val tempToken = "temp"
        val isLogin = authDataSource.tryLogin(localKakaoToken, tempToken)
        if (isLogin.success) return true
        return false
    }

    override suspend fun getNewAccessToken(): String? {
        val refreshToken = sharedPreferenceDataSource.getRefreshToken() ?: return null
        val accessToken = sharedPreferenceDataSource.getAccessToken() ?: return null
        val newTokens = authDataSource.getNewToken(accessToken, refreshToken)
        saveTokens(newTokens.accessToken, newTokens.refreshToken)

        return newTokens.accessToken
    }

    override suspend fun getAccessToken(): String? {
        // ??
        return sharedPreferenceDataSource.getAccessToken()
    }

    private fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPreferenceDataSource.setAccessToken(accessToken)
        sharedPreferenceDataSource.setRefreshToken(refreshToken)
    }

    private fun saveKakaoToken(kakaoToken: String) {
        sharedPreferenceDataSource.setKakaoToken(kakaoToken)
    }
}
