package com.recodream_aos.recordream.data.datasource.local // ktlint-disable package-name

interface SharedPreferenceDataSource {
    fun setAccessToken(accessToken: String)

    fun setRefreshToken(refreshToken: String)

    fun getAccessToken(): String?

    fun getRefreshToken(): String?
}
