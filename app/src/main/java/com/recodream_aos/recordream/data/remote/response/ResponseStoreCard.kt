package com.recodream_aos.recordream.data.remote.response

import androidx.annotation.DrawableRes

data class ResponseStoreCard(
    @DrawableRes val image: Int,
    val day: String,
    val description: String,
    val tag: List<ResponseStoreTag>
)
