package com.recodream_aos.recordream.data.entity.remote.response // ktlint-disable package-name

import kotlinx.serialization.Serializable

@Serializable
data class ResponseLogin(
    val userId: String,
    val isAlreadyUser: Boolean,
    val accessToken: String,
    val refreshToken: String,
    val nickname: String,
)
