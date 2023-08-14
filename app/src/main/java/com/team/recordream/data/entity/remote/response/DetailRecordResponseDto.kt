package com.team.recordream.data.entity.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailRecordResponseDto(
    @SerialName("data")
    val `data`: Data,
    @SerialName("message")
    val message: String,
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
) {
    @Serializable
    data class Data(
        @SerialName("_id")
        val id: String,
        @SerialName("content")
        val content: String,
        @SerialName("date")
        val date: String,
        @SerialName("emotion")
        val emotion: Int,
        @SerialName("genre")
        val genre: List<Int>,
        @SerialName("note")
        val note: String?,
        @SerialName("title")
        val title: String,
        @SerialName("voice")
        val voice: Voice?,
        @SerialName("writer")
        val writer: String,
    )
}

@Serializable
data class Voice(
    @SerialName("_id")
    val id: String,
    @SerialName("url")
    val url: String,
)
