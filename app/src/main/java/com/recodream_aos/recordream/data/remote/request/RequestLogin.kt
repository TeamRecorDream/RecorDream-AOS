package com.recodream_aos.recordream.data.remote.request // ktlint-disable package-name

import kotlinx.serialization.Serializable

@Serializable
data class RequestLogin(
    val kakaoToken: String,
    val fcmToken: String
)
