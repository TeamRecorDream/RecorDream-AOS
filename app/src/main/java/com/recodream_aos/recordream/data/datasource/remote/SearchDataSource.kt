package com.recodream_aos.recordream.data.datasource.remote

import com.example.domain.util.CustomResult
import com.recodream_aos.recordream.data.entity.remote.response.ResponseSearchDto

interface SearchDataSource {

    suspend fun postSearch(keyword: String): CustomResult<ResponseSearchDto.Data>
}
