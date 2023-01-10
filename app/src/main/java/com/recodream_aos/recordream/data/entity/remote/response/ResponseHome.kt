package com.recodream_aos.recordream.data.entity.remote.response

data class ResponseHome(

    val _id: String,
    val date: String,
    val dream_color: Int,
    val emotion: Int,
    val genre: List<Int>,
    val title: String
)
