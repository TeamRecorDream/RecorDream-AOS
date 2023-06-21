package com.recodream_aos.recordream.data.entity.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestRecordDto(
    val title: String,
    val date: String,
    val content: String?,
    val emotion: Int?,
    val genre: List<Int>?,
    val note: String?,
    val voice: String?,
)
