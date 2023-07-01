package com.recodream_aos.recordream.domain.model

data class SearchedRecord(
    val _id: String,
    val date: String,
    val emotion: Int,
    val genre: List<Int>,
    val title: String,
)
