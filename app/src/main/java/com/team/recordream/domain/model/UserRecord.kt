package com.team.recordream.domain.model

data class UserRecord(
    val nickname: String,
    val records: List<Record>,
) {
    data class Record(
        val id: String,
        val date: String,
        val emotion: Int,
        val genre: List<Int>,
        val title: String,
        val content: String,
        val voice: Boolean,
    )
}