package com.recodream_aos.recordream.data.entity.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestPushToken(
    val newToken: String,
    val originToken: String
)