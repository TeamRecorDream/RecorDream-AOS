package com.recodream_aos.recordream.mapper

import com.recodream_aos.recordream.data.entity.remote.response.ResponseVoiceDto
import com.recodream_aos.recordream.domain.model.VoiceRecord

fun ResponseVoiceDto.Data.toDomain(): VoiceRecord = VoiceRecord(
    id = this._id,
)
