package com.team.recordream.data.datasource.remote

import com.team.recordream.data.entity.remote.request.RequestNewRecordDto
import com.team.recordream.data.entity.remote.request.RequestRecordDto
import com.team.recordream.data.entity.remote.response.ResponseRecordDto
import com.team.recordream.data.entity.remote.response.ResponseVoiceDto
import com.team.recordream.domain.util.CustomResult
import okhttp3.MultipartBody

interface RecordDataSource {

    suspend fun postVoice(requestBody: MultipartBody.Part): CustomResult<ResponseVoiceDto.Data>

    suspend fun postRecord(requestBody: RequestRecordDto): CustomResult<ResponseRecordDto.Data>

    suspend fun patchRecord(recordId: String, requestBody: RequestNewRecordDto)
}
