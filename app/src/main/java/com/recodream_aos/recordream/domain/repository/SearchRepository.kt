package com.recodream_aos.recordream.domain.repository

import com.recodream_aos.recordream.domain.model.Record

// ktlint-disable package-name

interface SearchRepository {

    suspend fun postSearch(keyword: String): Record
}
