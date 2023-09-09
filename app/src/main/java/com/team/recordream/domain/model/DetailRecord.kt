package com.team.recordream.domain.model

data class DetailRecord(
    val id: String,
    val title: String,
    val date: String,
    val content: String?,
    val emotion: Int?,
    val genre: List<Int>,
    val note: String?,
    val voice: Voice?,
)
