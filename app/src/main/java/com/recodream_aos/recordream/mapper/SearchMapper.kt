package com.recodream_aos.recordream.mapper

import com.recodream_aos.recordream.data.entity.remote.response.ResponseSearchDto
import com.recodream_aos.recordream.data.entity.remote.response.ResponseSearchedRecordDto
import com.recodream_aos.recordream.domain.model.SearchResult
import com.recodream_aos.recordream.domain.model.SearchedRecord

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
