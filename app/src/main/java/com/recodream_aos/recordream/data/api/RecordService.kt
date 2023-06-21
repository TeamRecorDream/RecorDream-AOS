package com.recodream_aos.recordream.data.api

import com.recodream_aos.recordream.data.entity.remote.response.ResponseVoiceDto
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RecordService {

    @Multipart
    @POST("/voice")
    suspend fun postVoice(
        @Part file: MultipartBody.Part,
    ): ResponseVoiceDto
}
