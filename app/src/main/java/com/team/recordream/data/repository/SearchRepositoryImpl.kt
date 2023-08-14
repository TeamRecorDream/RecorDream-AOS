package com.team.recordream.data.repository

import com.team.recordream.data.datasource.remote.SearchDataSource
import com.team.recordream.domain.model.SearchResult
import com.team.recordream.domain.repository.SearchRepository
import com.team.recordream.domain.util.CustomResult
import com.team.recordream.domain.util.CustomResult.FAIL
import com.team.recordream.domain.util.CustomResult.SUCCESS
import com.team.recordream.domain.util.Error
import com.team.recordream.mapper.toDomain
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource,
) : SearchRepository {
    override suspend fun postSearch(keyword: String): CustomResult<SearchResult> {
        return when (val result = searchDataSource.postSearch(keyword)) {
            is SUCCESS -> SUCCESS(result.data.toDomain())
            is FAIL -> FAIL(Error.DisabledDataCall(result.error.errorMessage))
        }
    }
}
