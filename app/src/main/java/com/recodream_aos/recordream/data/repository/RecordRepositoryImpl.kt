package com.recodream_aos.recordream.data.repository // ktlint-disable package-name

import com.example.domain.util.CustomResult
import com.example.domain.util.CustomResult.FAIL
import com.example.domain.util.CustomResult.SUCCESS
import com.example.domain.util.Error
import com.recodream_aos.recordream.data.datasource.remote.RecordDataSource
import com.recodream_aos.recordream.domain.model.Record
import com.recodream_aos.recordream.domain.model.RecordId
import com.recodream_aos.recordream.domain.model.VoiceRecordId
import com.recodream_aos.recordream.domain.repository.RecordRepository
import com.recodream_aos.recordream.mapper.toDomain
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
        TODO("Not yet implemented")
    }

    companion object {
        private const val FILE_NAME = "file"
        private const val FILE_NAME_EXTENSION = "audio/3gpp"
    }
}
