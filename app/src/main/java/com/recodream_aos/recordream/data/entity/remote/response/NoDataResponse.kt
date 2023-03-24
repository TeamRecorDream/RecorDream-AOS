package com.recodream_aos.recordream.data.entity.remote.response

@kotlinx.serialization.Serializable
data class NoDataResponse(
    val message: String,
    val status: Int,
    val success: Boolean
)
