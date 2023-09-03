package com.team.recordream.data.repository // ktlint-disable package-name

import com.team.recordream.data.datasource.remote.RecordDataSource
import com.team.recordream.domain.model.Record
import com.team.recordream.domain.model.RecordId
import com.team.recordream.domain.model.VoiceRecordId
import com.team.recordream.domain.repository.RecordRepository
import com.team.recordream.domain.util.CustomResult
import com.team.recordream.domain.util.CustomResult.FAIL
import com.team.recordream.domain.util.CustomResult.SUCCESS
import com.team.recordream.domain.util.Error
import com.team.recordream.mapper.toDomain
import com.team.recordream.mapper.toNewRequestBody
import com.team.recordream.mapper.toRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordDataSource: RecordDataSource,
) : RecordRepository {
    override suspend fun postVoice(recordingFile: File): CustomResult<VoiceRecordId> {
        val requestFile = recordingFile.asRequestBody(FILE_NAME_EXTENSION.toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData(FILE_NAME, recordingFile.name, requestFile)

        return when (val result = recordDataSource.postVoice(filePart)) {
            is SUCCESS -> SUCCESS(result.data.toDomain())
            is FAIL -> FAIL(Error.DisabledDataCall(result.error.errorMessage))
        }
    }

    override suspend fun postRecord(record: Record): CustomResult<RecordId> {
        val requestBody = record.toRequestBody()

        return when (val result = recordDataSource.postRecord(requestBody)) {
            is SUCCESS -> SUCCESS(result.data.toDomain())
            is FAIL -> FAIL(Error.DisabledDataCall(result.error.errorMessage))
        }
    }

    override suspend fun updateRecord(recordId: String, record: Record): Result<Unit> {
        return runCatching {
            recordDataSource.patchRecord(
                recordId,
                record.toNewRequestBody(),
            )
        }
    }

    companion object {
        private const val FILE_NAME = "file"
        private const val FILE_NAME_EXTENSION = "audio/3gpp"
    }
}
