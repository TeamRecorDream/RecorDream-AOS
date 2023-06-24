package com.recodream_aos.recordream.data.api

import com.recodream_aos.recordream.data.entity.remote.response.ResponseSearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/record/storage/search")
    suspend fun postSearch(
        @Query("keyword") keyword: String,
    ): ResponseSearchDto
}
