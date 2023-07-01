package com.recodream_aos.recordream.data.repository

import com.example.domain.util.CustomResult
import com.example.domain.util.CustomResult.FAIL
import com.example.domain.util.CustomResult.SUCCESS
import com.example.domain.util.Error
import com.recodream_aos.recordream.data.datasource.remote.SearchDataSource
import com.recodream_aos.recordream.domain.model.SearchResult
import com.recodream_aos.recordream.domain.repository.SearchRepository
import com.recodream_aos.recordream.mapper.toDomain
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
