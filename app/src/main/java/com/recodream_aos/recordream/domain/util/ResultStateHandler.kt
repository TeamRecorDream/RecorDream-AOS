package com.recodream_aos.recordream.domain.util

sealed class CustomResult<out T : Any> {
    data class SUCCESS<out T : Any>(val data: T) : CustomResult<T>()
    data class FAIL(val error: Error) : CustomResult<Nothing>()
}

sealed class Error(val errorMessage: String) {
    data class DisabledDataCall(val error: String) : Error(error)
    object NoSuchId : Error("해당 ID가 없습니다")
}
