package com.team.recordream.data.datasource.local // ktlint-disable package-name

import javax.inject.Inject

class SharedPreferenceDataSourceImpl @Inject constructor(
    private val recordreamSharedPreference: AuthTokenStorage,
) : SharedPreferenceDataSource {

    override fun setAccessToken(accessToken: String?) {
        recordreamSharedPreference.setValue(ACCESS_TOKEN, accessToken ?: "")
    }

    override fun setRefreshToken(refreshToken: String?) {
        recordreamSharedPreference.setValue(REFRESH_TOKEN, refreshToken ?: "")
    }

    override fun getAccessToken(): String =
        recordreamSharedPreference.getValue(ACCESS_TOKEN) ?: ""

    override fun getRefreshToken(): String =
        recordreamSharedPreference.getValue(REFRESH_TOKEN) ?: ""

    override fun removeAccessToken() {
        recordreamSharedPreference.deleteSharedAccessToken()
    }

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
    }
}
