package com.recodream_aos.recordream.data.entity.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseHome(
    val nickname: String,
    @SerialName("records")
    val records: List<Record>
) {
    @Serializable
    data class Record(
        @SerialName("_id")
        val id: String,
        @SerialName("date")
        val date: String,
        val dream_color: Int,
        val emotion: Int,
        val genre: List<Int>,
        val title: String,
        @SerialName("isExistVoice")
        val voice: Boolean
    )
}
