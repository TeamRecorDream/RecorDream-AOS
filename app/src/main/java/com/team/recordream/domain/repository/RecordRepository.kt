package com.team.recordream.domain.repository

import com.team.recordream.domain.model.Record
import com.team.recordream.domain.model.RecordId
import com.team.recordream.domain.model.VoiceRecordId
import com.team.recordream.domain.util.CustomResult
import java.io.File

interface RecordRepository {
    suspend fun postVoice(recordingFile: File): CustomResult<VoiceRecordId>

    suspend fun postRecord(record: Record): CustomResult<RecordId>

    suspend fun updateRecord(recordId: String, record: Record): Result<Unit>
}
