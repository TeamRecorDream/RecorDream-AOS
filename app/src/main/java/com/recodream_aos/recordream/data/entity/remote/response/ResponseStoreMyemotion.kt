package com.recodream_aos.recordream.data.entity.remote.response

import androidx.annotation.DrawableRes

data class ResponseStoreMyemotion(
    @DrawableRes var image: Int,
    val emotion: String
)
