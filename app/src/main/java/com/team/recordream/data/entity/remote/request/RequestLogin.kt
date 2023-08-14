package com.team.recordream.data.entity.remote.request // ktlint-disable package-name

import kotlinx.serialization.Serializable

@Serializable
data class RequestLogin(
    val kakaoToken: String,
    val fcmToken: String,
)
