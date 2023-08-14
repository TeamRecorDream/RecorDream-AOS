package com.team.recordream.data.entity.remote.response // ktlint-disable package-name

import kotlinx.serialization.Serializable

@Serializable
data class NonNullResponseWrapper<T>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T,
)
