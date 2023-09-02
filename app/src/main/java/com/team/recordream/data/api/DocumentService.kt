package com.team.recordream.data.api

import com.team.recordream.data.entity.remote.response.DetailRecordResponseDto
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface DocumentService {
    @DELETE("record/{recordId}")
    fun deleteDetailRecord(
        @Path("recordId") recordId: String,
    ): ResponseWrapper<Unit>

    @GET("record/{recordId}")
    suspend fun getDetailRecord(
        @Path("recordId") recordId: String,
    ): DetailRecordResponseDto
}
