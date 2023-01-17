package com.recodream_aos.recordream.data.api

import com.recodream_aos.recordream.data.entity.remote.response.ResponseStorage
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface StorageService {

    @GET("record/storage/list")
    suspend fun getStorage(
        @Query("filter") filter: Int
    ): ResponseWrapper<ResponseStorage>
}