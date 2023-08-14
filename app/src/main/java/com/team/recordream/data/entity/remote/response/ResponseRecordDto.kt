package com.team.recordream.data.entity.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseRecordDto(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean,
) {
    @Serializable
    data class Data(
        val _id: String,
    )
}
