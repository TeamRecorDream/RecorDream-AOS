package com.recodream_aos.recordream.data.datasource.remote

import com.recodream_aos.recordream.domain.util.CustomResult
import com.recodream_aos.recordream.domain.util.CustomResult.FAIL
import com.recodream_aos.recordream.domain.util.CustomResult.SUCCESS
import com.recodream_aos.recordream.domain.util.Error
import com.recodream_aos.recordream.data.api.SearchService
import com.recodream_aos.recordream.data.entity.remote.response.ResponseSearchDto
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
