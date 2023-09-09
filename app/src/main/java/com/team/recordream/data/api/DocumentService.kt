package com.team.recordream.data.api

import com.team.recordream.data.entity.remote.response.DetailRecordResponseDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface DocumentService {
    @DELETE("record/{recordId}")
    suspend fun deleteDetailRecord(
        @Path("recordId") recordId: String,
    ): Response<Unit>

    @GET("record/{recordId}")
    suspend fun getDetailRecord(
        @Path("recordId") recordId: String,
    ): DetailRecordResponseDto
}
