package com.team.recordream.data.repository // ktlint-disable package-name

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.user.UserApiClient
import com.team.recordream.data.datasource.local.SharedPreferenceDataSource
import com.team.recordream.data.datasource.remote.AuthDataSource
import com.team.recordream.data.entity.remote.response.NoDataResponse
import com.team.recordream.data.entity.remote.response.ResponseNewToken
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import com.team.recordream.domain.repository.AuthRepository
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource,
) : AuthRepository {
    override suspend fun postLogin(kakaoToken: String, fcmToken: String): Boolean {
        return try {
            val tokens = authDataSource.postLogin(kakaoToken, fcmToken)
            saveTokens(tokens.accessToken, tokens.refreshToken)
            true
        } catch (exceptionMessage: Exception) {
            Timber.e("AuthRepositoryImpl3_postLogin : $exceptionMessage")
            false
        }
    }

    override suspend fun postToken(): Boolean {
        return try {
            Log.d("123123", sharedPreferenceDataSource.getAccessToken().toString())
            val localAccessToken = sharedPreferenceDataSource.getAccessToken()
            val localRefreshToken = sharedPreferenceDataSource.getRefreshToken()
            val postTokenInfo = authDataSource.postToken(localAccessToken, localRefreshToken)
            saveTokens(postTokenInfo.data!!.accessToken, postTokenInfo.data.refreshToken)

            Timber.e("AuthRepositoryImpl3_postToken : $postTokenInfo")
            true
        } catch (exceptionMessage: Exception) {
            Timber.e("AuthRepositoryImpl3_postToken : $exceptionMessage")
            tryLogin(exceptionMessage)
        }
    }

    override suspend fun getNewAccessToken(): String {
        with(sharedPreferenceDataSource) {
            val postTokenInfo = authDataSource.postToken(getAccessToken(), getRefreshToken())
            saveNewTokens(postTokenInfo)
            return postTokenInfo.data!!.accessToken
        }
    }

    override suspend fun getAccessToken(): String {
        return sharedPreferenceDataSource.getAccessToken()
    }

    override fun unLinkKakaoAccount(initSuccessWithdraw: (Boolean) -> Unit) {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Timber.d("kakao", "unLinkKakaoAccount: 연결 끊기 실패")
                Timber.tag("kakao").e(error, "연결 끊기 실패")
                initSuccessWithdraw(false)
            } else {
                Timber.d("kakao", "unLinkKakaoAccount: 연결 끊기 성공. SDK에서 토큰 삭제 됨")
                Timber.tag("kakao").d("연결 끊기 성공. SDK에서 토큰 삭제 됨")
                initSuccessWithdraw(true)
            }
        }
    }

    override fun logoutKakaoAccount(logout: (Boolean) -> Unit) {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Timber.d("kakao", "logout: 연결 끊기 실패")
                Timber.tag("kakao").e(error, "연결 끊기 실패")
                logout(false)
            } else {
                Timber.d("kakao", "logout: 연결 끊기 성공. SDK에서 토큰 삭제 됨")
                Timber.tag("kakao").d("연결 끊기 성공. SDK에서 토큰 삭제 됨")
                logout(true)
            }
        }
    }

    private fun saveNewTokens(postTokenInfo: ResponseWrapper<ResponseNewToken>) {
        if (postTokenInfo.success) {
            val newAccessToken = postTokenInfo.data!!.accessToken
            val refreshToken = postTokenInfo.data.refreshToken
            saveTokens(newAccessToken, refreshToken)
        }
    }

    private fun saveTokens(accessToken: String?, refreshToken: String?) {
        sharedPreferenceDataSource.setAccessToken(accessToken)
        sharedPreferenceDataSource.setRefreshToken(refreshToken)
    }

    private fun tryLogin(exceptionMessage: Exception): Boolean {
        if (exceptionMessage is HttpException) {
            return when (exceptionMessage.code()) {
                NO_TOKEN -> false
                EXPIRED_REFRESH_TOKEN -> false
                USABLE_TOKENS -> true
                else -> false
            }
        }
        return false
    }

    override suspend fun getFcmToken(getFcmToken: (String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                getFcmToken(requireNotNull(task.result))
                Timber.d("fcm", "getFcmToken: ${FirebaseMessaging.getInstance().token}")
            },
        )
    }

    override suspend fun patchSignOut(): Result<NoDataResponse> {
        return kotlin.runCatching {
            authDataSource.patchSignOut()
        }.onSuccess {
            Timber.d("MypageUserRepositoryImpl0", "patchSignOut: ")
        }.onFailure {
            Timber.d("MypageUserRepositoryImpl1", "patchSignOut: ${it.message}")
            Timber.d("MypageUserRepositoryImpl", "postPushAlam OnFail: ${it.message}")
            it.message
        }
    }

    override suspend fun deleteUser(): Result<NoDataResponse> =
        kotlin.runCatching { authDataSource.deleteUser() }
            .onFailure { Timber.d("delete", "deleteUser: fail") }

    companion object {
        const val NO_TOKEN = 400
        const val EXPIRED_REFRESH_TOKEN = 401
        const val USABLE_TOKENS = 403
    }
}
