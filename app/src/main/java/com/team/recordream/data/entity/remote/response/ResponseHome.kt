package com.team.recordream.data.entity.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseHome(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("records")
    val records: List<Record>,
) {
    @Serializable
    data class Record(
        @SerialName("_id")
        val id: String,
        @SerialName("date")
        val date: String,
        @SerialName("emotion")
        val emotion: Int,
        @SerialName("genre")
        val genre: List<Int>,
        @SerialName("title")
        val title: String,
        @SerialName("content")
        val content: String?,
        @SerialName("isExistVoice")
        val voice: Boolean,
    )
}
