package com.team.recordream.mapper

import com.team.recordream.data.entity.remote.request.RequestNewRecordDto
import com.team.recordream.data.entity.remote.request.RequestRecordDto
import com.team.recordream.data.entity.remote.response.ResponseRecordDto
import com.team.recordream.data.entity.remote.response.ResponseVoiceDto
import com.team.recordream.domain.model.Record
import com.team.recordream.domain.model.RecordId
import com.team.recordream.domain.model.VoiceRecordId

fun ResponseVoiceDto.Data.toDomain(): VoiceRecordId = VoiceRecordId(
    id = _id,
)

fun Record.toRequestBody(): RequestRecordDto = RequestRecordDto(
    title = title,
    date = date,
    content = content,
    emotion = emotion,
    genre = genre,
    note = note,
    voice = voice,
)

fun Record.toNewRequestBody(): RequestNewRecordDto = RequestNewRecordDto(
    title = title,
    date = date,
    content = content,
    emotion = emotion,
    genre = genre,
    note = note,
)


fun ResponseRecordDto.Data.toDomain(): RecordId = RecordId(
    id = _id,
)
