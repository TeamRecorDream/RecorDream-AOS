package com.recodream_aos.recordream.domain.repository

import com.example.domain.util.CustomResult
import com.recodream_aos.recordream.domain.model.SearchResult

// ktlint-disable package-name

interface SearchRepository {

    suspend fun postSearch(keyword: String): CustomResult<SearchResult>
}
