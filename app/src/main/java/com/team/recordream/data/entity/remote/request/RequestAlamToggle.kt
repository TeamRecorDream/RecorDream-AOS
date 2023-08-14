package com.team.recordream.data.entity.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestAlamToggle(
    val isActive: Boolean,
)
