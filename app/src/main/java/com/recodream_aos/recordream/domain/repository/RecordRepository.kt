package com.recodream_aos.recordream.domain.repository // ktlint-disable package-name

interface RecordRepository {
    suspend fun getCalendar(): Boolean
}
