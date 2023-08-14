package com.team.recordream.data.datasource.remote

import com.team.recordream.data.entity.remote.response.ResponseSearchDto
import com.team.recordream.domain.util.CustomResult

interface SearchDataSource {

    suspend fun postSearch(keyword: String): CustomResult<ResponseSearchDto.Data>
}
