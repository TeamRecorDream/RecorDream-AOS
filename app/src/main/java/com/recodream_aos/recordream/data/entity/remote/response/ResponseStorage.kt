package com.recodream_aos.recordream.data.entity.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseStorage(
    val records: List<Record>,
    val recordsCount: Int
) {
    @Serializable
    data class Record(
        val _id: String,
        val date: String,
        val emotion: Int,
        val genre: List<Int>,
        val title: String
    )
}