package com.team.recordream.presentation.home.model

data class UserRecords(
    val id: String,
    val date: String,
    val emotion: Int,
    val genre: List<Int>,
    val title: String,
    val content: String,
    val voice: Boolean,
)
