package com.recodream_aos.recordream.presentation.document2.model

data class Dummy(
    val writer: String,
    val date: String,
    val title: String,
    val voice: String?,
    val content: String?,
    val emotion: Int,
    val genre: List<Int>,
    val note: String?,
)
