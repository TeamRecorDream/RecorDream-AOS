package com.recodream_aos.recordream.data.entity.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMypageUser(
    val email: String,
    val isActive: Boolean,
    val nickname: String,
    val time: String?
)

