package com.team.recordream.data.entity.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestNewRecordDto(
    @SerialName("title")
    val title: String,
    @SerialName("date")
    val date: String,
    @SerialName("content")
    val content: String?,
    @SerialName("emotion")
    val emotion: Int?,
    @SerialName("genre")
    val genre: List<Int>?,
    @SerialName("note")
    val note: String?,
)
