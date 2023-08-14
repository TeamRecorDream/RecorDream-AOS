package com.team.recordream.mapper

import com.team.recordream.data.entity.remote.request.RequestRecordDto
import com.team.recordream.data.entity.remote.response.ResponseRecordDto
import com.team.recordream.data.entity.remote.response.ResponseVoiceDto
import com.team.recordream.domain.model.Record
import com.team.recordream.domain.model.RecordId
import com.team.recordream.domain.model.VoiceRecordId

fun ResponseVoiceDto.Data.toDomain(): VoiceRecordId = VoiceRecordId(
    id = this._id,
)

fun Record.toRequestBody(): RequestRecordDto = RequestRecordDto(
    title = this.title,
    date = this.date,
    content = this.content,
    emotion = this.emotion,
    genre = this.genre,
    note = this.note,
    voice = this.voice,
)

fun ResponseRecordDto.Data.toDomain(): RecordId = RecordId(
    id = this._id,
)
