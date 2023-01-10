package com.recodream_aos.recordream.data.entity.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseLogin(
    val isAlreadyUser: Boolean,
    val accessToken: String,
    val refreshToken: String,
    val nickname: String
)
