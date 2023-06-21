package com.recodream_aos.recordream.data.datasource.remote

import com.example.domain.util.CustomResult
import com.recodream_aos.recordream.data.entity.remote.response.ResponseVoiceDto
import okhttp3.MultipartBody

interface RecordDataSource {

    suspend fun postVoice(requestBody: MultipartBody.Part): CustomResult<ResponseVoiceDto.Data>
}
