package com.recodream_aos.recordream.data.remote.response

import androidx.annotation.DrawableRes

data class ResponseStoreMyemotion(
    @DrawableRes var image: Int,
    val emotion: String
)
