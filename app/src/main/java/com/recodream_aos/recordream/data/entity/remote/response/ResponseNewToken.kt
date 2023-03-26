package com.recodream_aos.recordream.data.entity.remote.response // ktlint-disable package-name

import kotlinx.serialization.Serializable

@Serializable
data class ResponseNewToken(
    val accessToken: String,
    val refreshToken: String
)
