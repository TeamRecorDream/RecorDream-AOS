package com.recodream_aos.recordream.data.repository // ktlint-disable package-name

import android.util.Log
import com.recodream_aos.recordream.data.datasource.local.SharedPreferenceDataSource
import com.recodream_aos.recordream.data.datasource.remote.AuthDataSource
import com.recodream_aos.recordream.data.entity.remote.response.ResponseNewToken
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import com.recodream_aos.recordream.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : AuthRepository {
    override suspend fun postLogin(kakaoToken: String, fcmToken: String): Boolean {
        return try {
            val tokens = authDataSource.postLogin(kakaoToken, fcmToken)
            saveTokens(tokens.accessToken, tokens.refreshToken)
            true
        } catch (exceptionMessage: Exception) {
            Timber.d(exceptionMessage.toString())
            false
        }
    }

    override suspend fun postToken(): Boolean {
        return try {
            val localAccessToken = sharedPreferenceDataSource.getAccessToken()
            val localRefreshToken = sharedPreferenceDataSource.getRefreshToken()

            val postTokenInfo =
                authDataSource.postToken(localAccessToken, localRefreshToken)
            Log.d("안녕", "안녕")
            Log.d("안녕", postTokenInfo.toString())
            true

            //  tryLogin(postTokenInfo)
        } catch (exceptionMessage: Exception) {
            Timber.d(exceptionMessage.toString())
            false
        }
    }
//
//    override suspend fun getNewAccessToken(): String {
//        val localAccessToken = sharedPreferenceDataSource.getAccessToken()
//        val localRefreshToken = sharedPreferenceDataSource.getRefreshToken()
//        val postTokenInfo =
//            authDataSource.postToken(localAccessToken, localRefreshToken)
//        val newAccessToken = postTokenInfo.data.accessToken
//        val refreshToken = postTokenInfo.data.refreshToken
//        saveTokens(newAccessToken, refreshToken)
//
//        return postTokenInfo.data.accessToken
//    }

    override suspend fun getAccessToken(): String {
        return sharedPreferenceDataSource.getAccessToken()
    }

    private fun saveTokens(accessToken: String?, refreshToken: String?) {
        sharedPreferenceDataSource.setAccessToken(accessToken)
        sharedPreferenceDataSource.setRefreshToken(refreshToken)
    }

    private fun tryLogin(postTokenInfo: ResponseWrapper<ResponseNewToken>): Boolean {
        Log.d("안녕", "tryLogin은 넘어옵니다")
        val newAccessToken = postTokenInfo.data?.accessToken
        val refreshToken = postTokenInfo.data?.refreshToken
        Log.d("안녕", "${postTokenInfo.status}tryLogin입니다 현재")
        return when (postTokenInfo.status) {
            EXPIRED_ACCESS_TOKEN -> {
                saveTokens(newAccessToken, refreshToken)
                true
            }
            NO_TOKEN -> false
            EXPIRED_REFRESH_TOKEN -> false
            USABLE_TOKENS -> true
            else -> false
        }
    }

    companion object {
        const val EXPIRED_ACCESS_TOKEN = 200
        const val NO_TOKEN = 400
        const val EXPIRED_REFRESH_TOKEN = 401
        const val USABLE_TOKENS = 403
        const val SERVER_ERROR = 500
    }
}
