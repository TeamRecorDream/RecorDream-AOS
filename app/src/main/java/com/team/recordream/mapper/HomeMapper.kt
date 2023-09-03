package com.team.recordream.mapper

import com.team.recordream.data.entity.remote.response.NonNullResponseWrapper
import com.team.recordream.data.entity.remote.response.ResponseHome
import com.team.recordream.domain.model.UserRecord

fun NonNullResponseWrapper<ResponseHome>.toDomain(): UserRecord = UserRecord(
    nickname = data.nickname,
    records = data.records.map { it.toDomain() },
)

fun ResponseHome.Record.toDomain(): UserRecord.Record = UserRecord.Record(
    id = id,
    date = date,
    emotion = emotion,
    genre = genre,
    title = title,
    content = content ?: "",
    voice = voice,
)
