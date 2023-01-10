package com.recodream_aos.recordream.data.datasource.local // ktlint-disable package-name

import javax.inject.Inject

class SharedPreferenceDataSourceImpl @Inject constructor(
    private val recordreamSharedPreference: RecordreamSharedPreference
) : SharedPreferenceDataSource {
    override fun setKakaoToken(kakaoToken: String) {
        recordreamSharedPreference.setValue(KAKAO_TOKEN, kakaoToken)
    }

    override fun setAccessToken(accessToken: String) {
        recordreamSharedPreference.setValue(ACCESS_TOKEN, accessToken)
    }

    override fun setRefreshToken(refreshToken: String) {
        recordreamSharedPreference.setValue(REFRESH_TOKEN, refreshToken)
    }

    override fun getAccessToken(): String? =
        recordreamSharedPreference.getValue(ACCESS_TOKEN)?.takeIf { it.isNotEmpty() }

    override fun getRefreshToken(): String? =
        recordreamSharedPreference.getValue(REFRESH_TOKEN)?.takeIf { it.isNotEmpty() }

    override fun getKakaoToken(): String? =
        recordreamSharedPreference.getValue(KAKAO_TOKEN) // 리퀘스트 널 값 처리, TAG : 서경

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val KAKAO_TOKEN = "KAKAO_TOKEN"
    }
}
