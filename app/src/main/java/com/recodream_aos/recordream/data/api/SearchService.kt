package com.recodream_aos.recordream.data.api

import com.recodream_aos.recordream.data.entity.remote.response.ResponseSearchDto
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchService {

    @POST("/record/storage/search")
    suspend fun postSearch(
        @Query("keyword") keyword: String,
    ): ResponseWrapper<ResponseSearchDto>
}
