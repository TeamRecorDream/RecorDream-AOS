package com.recodream_aos.recordream.data.entity.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseSearchDto(
    val records: List<ResponseSearchedRecordDto>,
    val recordsCount: Int,
) {
    @Serializable
    data class ResponseSearchedRecordDto(
        val _id: String,
        val date: String,
        val emotion: Int,
        val genre: List<Int>,
        val title: String,
    )
}
