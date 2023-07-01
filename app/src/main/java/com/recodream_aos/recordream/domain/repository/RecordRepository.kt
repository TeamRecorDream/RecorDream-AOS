package com.recodream_aos.recordream.domain.repository

import com.example.domain.util.CustomResult
import com.recodream_aos.recordream.domain.model.Record
import com.recodream_aos.recordream.domain.model.RecordId
import com.recodream_aos.recordream.domain.model.VoiceRecordId
import java.io.File

interface RecordRepository {
    suspend fun postVoice(recordingFile: File): CustomResult<VoiceRecordId>

    suspend fun postRecord(record: Record): CustomResult<RecordId>
}
