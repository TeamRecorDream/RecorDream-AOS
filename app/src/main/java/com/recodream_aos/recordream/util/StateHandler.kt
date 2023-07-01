package com.recodream_aos.recordream.util

sealed interface State {
    object VALID : State
    object INVALID : State
    object DISCONNECT : State
    object IDLE : State
}
