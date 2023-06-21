package com.recodream_aos.recordream.data.datasource.remote

import com.recodream_aos.recordream.data.entity.remote.response.ResponseSearchDto

interface SearchDataSource {

    suspend fun postSearch(keyword: String): ResponseSearchDto
}
