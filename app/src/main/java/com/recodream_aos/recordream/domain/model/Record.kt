package com.recodream_aos.recordream.domain.model

data class Record(
    val title: String,
    val date: String,
    val content: String?,
    val emotion: Int?,
    val genre: List<Int>?,
    val note: String?,
    val voice: String?,
)
