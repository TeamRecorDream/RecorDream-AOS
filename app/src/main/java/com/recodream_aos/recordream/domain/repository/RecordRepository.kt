package com.recodream_aos.recordream.domain.repository

import com.example.domain.util.CustomResult
import com.recodream_aos.recordream.domain.model.VoiceRecord
import java.io.File

// ktlint-disable package-name

interface RecordRepository {
    suspend fun postVoice(recordingFile: File): CustomResult<VoiceRecord>
}
