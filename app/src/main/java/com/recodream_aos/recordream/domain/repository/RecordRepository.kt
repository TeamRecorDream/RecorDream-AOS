package com.recodream_aos.recordream.domain.repository // ktlint-disable package-name

import android.icu.util.Calendar

interface RecordRepository {
    suspend fun getCalendar(): Calendar
}
