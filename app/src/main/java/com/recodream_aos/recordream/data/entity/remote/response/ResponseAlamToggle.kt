package com.recodream_aos.recordream.data.entity.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseAlamToggle(
    val isActive: Boolean
)