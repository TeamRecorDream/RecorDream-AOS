package com.recodream_aos.recordream.data.entity.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestNewToken(
    val access: String,
    val refresh: String
)
