package com.recodream_aos.recordream.data.entity.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseHome(
    val nickname: String,
    val records: List<Record>,
) {
    @Serializable
    data class Record(
        @SerialName("_id")
        val id: String,
        val date: String,
        val emotion: Int,
        val genre: List<Int>,
        val title: String,
        val content: String,
        @SerialName("isExistVoice")
        val voice: Boolean,
    )
}
