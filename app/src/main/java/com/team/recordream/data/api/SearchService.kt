package com.team.recordream.data.api

import com.team.recordream.data.entity.remote.response.ResponseSearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/record/storage/search")
    suspend fun postSearch(
        @Query("keyword") keyword: String,
    ): ResponseSearchDto
}
