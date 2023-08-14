package com.team.recordream.data.datasource.remote

import com.team.recordream.data.api.SearchService
import com.team.recordream.data.entity.remote.response.ResponseSearchDto
import com.team.recordream.domain.util.CustomResult
import com.team.recordream.domain.util.CustomResult.FAIL
import com.team.recordream.domain.util.CustomResult.SUCCESS
import com.team.recordream.domain.util.Error
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val searchService: SearchService,
) : SearchDataSource {
    override suspend fun postSearch(keyword: String): CustomResult<ResponseSearchDto.Data> {
        val result = searchService.postSearch(keyword)
        return when (result.success) {
            true -> SUCCESS(result.data)
            false -> FAIL(Error.DisabledDataCall(result.message))
        }
    }
}
