package com.recodream_aos.recordream.data.datasource.remote

import com.recodream_aos.recordream.domain.util.CustomResult
import com.recodream_aos.recordream.domain.util.CustomResult.FAIL
import com.recodream_aos.recordream.domain.util.CustomResult.SUCCESS
import com.recodream_aos.recordream.domain.util.Error
import com.recodream_aos.recordream.data.api.RecordService
import com.recodream_aos.recordream.data.entity.remote.request.RequestRecordDto
import com.recodream_aos.recordream.data.entity.remote.response.ResponseRecordDto
import com.recodream_aos.recordream.data.entity.remote.response.ResponseVoiceDto
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
