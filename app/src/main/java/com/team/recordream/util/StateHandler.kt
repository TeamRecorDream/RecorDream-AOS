package com.team.recordream.util

sealed interface StateHandler {
    data class VALID(val recordId: String) : StateHandler
    object INVALID : StateHandler
    object DISCONNECT : StateHandler
    object IDLE : StateHandler
}
