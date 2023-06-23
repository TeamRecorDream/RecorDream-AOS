package com.recodream_aos.recordream.data.entity.remote.response // ktlint-disable package-name

import kotlinx.serialization.Serializable

@Serializable
data class ResponseSearchedRecordDto(
    val _id: String,
    val date: String,
    val emotion: Int,
    val genre: List<Int>,
    val title: String,
)
