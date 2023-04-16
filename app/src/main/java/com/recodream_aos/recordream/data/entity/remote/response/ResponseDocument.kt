package com.recodream_aos.recordream.data.entity.remote.response

import android.speech.tts.Voice
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDocument(
    @SerialName("_id")
    val id: String?,
    @SerialName("writer")
    val writer: String?,
    @SerialName("date")
    val date: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("content")
    val content: String?,
    @SerialName("emotion")
    val emotion: Int?,
    @SerialName("genre")
    val genre: List<Int>,
    @SerialName("note")
    val note: String?,
    @SerialName("voice")
    val voice: Voice?
) {
    @Serializable
    data class Voice(
        @SerialName("_id")
        val voice_id: String?,
        @SerialName("url")
        val url: String?
    )
}
