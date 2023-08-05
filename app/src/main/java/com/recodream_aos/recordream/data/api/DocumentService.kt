package com.recodream_aos.recordream.data.api

import com.recodream_aos.recordream.data.entity.remote.response.ResponseDocument
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface DocumentService {
    @GET("record/{recordId}")
    fun getDetailRecord(
        @Path("recordId") recordId: String,
    ): ResponseWrapper<ResponseDocument>

    @DELETE("record/{recordId}")
    fun deleteDetailRecord(
        @Path("recordId") recordId: String,
    )
}
