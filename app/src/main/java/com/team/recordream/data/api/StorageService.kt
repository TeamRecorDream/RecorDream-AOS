package com.team.recordream.data.api

import com.team.recordream.data.entity.remote.response.ResponseStorage
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface StorageService {
    @GET("record/storage/list")
    suspend fun getStorage(
        @Query("filter") filter: Int,
    ): ResponseWrapper<ResponseStorage>
}
