package com.team.recordream.data.datasource.remote

import com.team.recordream.domain.util.CustomResult
import com.team.recordream.domain.util.CustomResult.FAIL
import com.team.recordream.domain.util.CustomResult.SUCCESS
import com.team.recordream.domain.util.Error
import com.team.recordream.data.api.RecordService
import com.team.recordream.data.entity.remote.request.RequestRecordDto
import com.team.recordream.data.entity.remote.response.ResponseRecordDto
import com.team.recordream.data.entity.remote.response.ResponseVoiceDto
import okhttp3.MultipartBody
import javax.inject.Inject

class RecordDataSourceImpl @Inject constructor(
    private val recordService: RecordService,
) : RecordDataSource {
    override suspend fun postVoice(requestBody: MultipartBody.Part): CustomResult<ResponseVoiceDto.Data> {
        val result = recordService.postVoice(requestBody)

        return when (result.success) {
            true -> SUCCESS(result.data)
            false -> FAIL(Error.DisabledDataCall(result.message))
        }
    }

    override suspend fun postRecord(requestBody: RequestRecordDto): CustomResult<ResponseRecordDto.Data> {
        val result = recordService.postRecord(requestBody)

        return when (result.success) {
            true -> SUCCESS(result.data)
            false -> FAIL(Error.DisabledDataCall(result.message))
        }
    }
}
