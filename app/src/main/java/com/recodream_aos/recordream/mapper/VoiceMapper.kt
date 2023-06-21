package com.recodream_aos.recordream.mapper

import com.recodream_aos.recordream.data.entity.remote.response.ResponseVoice
import com.recodream_aos.recordream.domain.model.VoiceRecord

fun ResponseVoice.Data.toDomain(): VoiceRecord = VoiceRecord(
    id = this._id,
)
