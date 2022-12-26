package com.team.recordream.domain.repository

import com.team.recordream.domain.model.SearchResult
import com.team.recordream.domain.util.CustomResult

// ktlint-disable package-name

interface SearchRepository {

    suspend fun postSearch(keyword: String): CustomResult<SearchResult>
}
