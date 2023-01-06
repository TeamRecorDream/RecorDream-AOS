package com.recodream_aos.recordream.data.entity.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseWrapper<T>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T? = null
)
