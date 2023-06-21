package com.recodream_aos.recordream.domain.repository

import com.recodream_aos.recordream.domain.model.VoiceRecord
import java.io.File

// ktlint-disable package-name

interface RecordRepository {
    fun postVoice(onSuccess: (VoiceRecord) -> Unit, recordingFile: File)
}
