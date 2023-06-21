package com.recodream_aos.recordream.data.repository // ktlint-disable package-name

import com.recodream_aos.recordream.data.datasource.remote.RecordDataSource
import com.recodream_aos.recordream.domain.model.VoiceRecord
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
    override fun postVoice(onSuccess: (VoiceRecord) -> Unit, recordingFile: File) {
        val filePart = MultipartBody.Part.createFormData(
            "file",
            recordingFile.name,
            recordingFile.asRequestBody("/audioRecord.3gp".toMediaTypeOrNull()),
        )

        val requestFile = recordingFile.asRequestBody("audio/3gpp".toMediaTypeOrNull())
        val filePart2 = MultipartBody.Part.createFormData("file", recordingFile.name, requestFile)

        recordDataSource.postVoice(onSuccess = {
            onSuccess.invoke(it.data.toDomain())
        }, filePart2)
    }
}
