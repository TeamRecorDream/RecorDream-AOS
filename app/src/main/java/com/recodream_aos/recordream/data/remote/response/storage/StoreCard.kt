package com.recodream_aos.recordream.data.remote.response.storage

import com.google.gson.annotations.SerializedName

data class StoreCard(

    val id: String,
    val date: String,
    val emotion: Int,
    val genre: List<Int>,
    @SerializedName("title")
    val description: String

)
