package com.team.recordream.data.api

import com.team.recordream.data.entity.remote.request.RequestNewRecordDto
import com.team.recordream.data.entity.remote.request.RequestRecordDto
import com.team.recordream.data.entity.remote.response.ResponseRecordDto
import com.team.recordream.data.entity.remote.response.ResponseVoiceDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface RecordService {

    @Multipart
    @POST("/voice")
    suspend fun postVoice(
        @Part file: MultipartBody.Part,
    ): ResponseVoiceDto

    @POST("/record")
    suspend fun postRecord(
        @Body requestBody: RequestRecordDto,
    ): ResponseRecordDto

    @PATCH("/record/{recordId}")
    suspend fun patchRecord(
        @Path("recordId") recordId: String,
        @Body requestBody: RequestNewRecordDto,
    )
}
