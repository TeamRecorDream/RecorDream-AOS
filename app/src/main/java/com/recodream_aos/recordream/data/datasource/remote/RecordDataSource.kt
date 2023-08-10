package com.recodream_aos.recordream.data.datasource.remote

import com.recodream_aos.recordream.domain.util.CustomResult
import com.recodream_aos.recordream.data.entity.remote.request.RequestRecordDto
import com.recodream_aos.recordream.data.entity.remote.response.ResponseRecordDto
import com.recodream_aos.recordream.data.entity.remote.response.ResponseVoiceDto
import okhttp3.MultipartBody

interface RecordDataSource {

    suspend fun postVoice(requestBody: MultipartBody.Part): CustomResult<ResponseVoiceDto.Data>

    suspend fun postRecord(requestBody: RequestRecordDto): CustomResult<ResponseRecordDto.Data>
}
