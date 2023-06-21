package com.recodream_aos.recordream.data.datasource.remote

import com.recodream_aos.recordream.data.entity.remote.response.ResponseSearchDto

class SearchDataSourceImpl : SearchDataSource {
    override suspend fun postSearch(keyword: String): ResponseSearchDto {
    }
}
