package com.team.recordream.mapper

import com.team.recordream.data.entity.remote.response.ResponseSearchDto
import com.team.recordream.data.entity.remote.response.ResponseSearchedRecordDto
import com.team.recordream.domain.model.SearchResult
import com.team.recordream.domain.model.SearchedRecord

fun ResponseSearchDto.Data.toDomain(): SearchResult = SearchResult(
    records = this.records.map { it.toDomain() },
    recordsCount = this.recordsCount,
)

fun ResponseSearchedRecordDto.toDomain(): SearchedRecord = SearchedRecord(
    _id = this._id,
    date = this.date,
    emotion = this.emotion,
    genre = this.genre,
    title = this.title,

    )
