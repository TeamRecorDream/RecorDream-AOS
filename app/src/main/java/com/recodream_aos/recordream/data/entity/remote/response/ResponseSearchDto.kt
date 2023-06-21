package com.recodream_aos.recordream.data.entity.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseSearchDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Data,
) {
    @Serializable
    data class Data(
        val recordsCount: Int,
        val records: List<ResponseSearchedRecordDto>,
    )
}
