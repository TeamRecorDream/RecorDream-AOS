package com.team.recordream.data.datasource.remote

import com.team.recordream.data.entity.remote.response.DetailRecordResponseDto
import com.team.recordream.data.entity.remote.response.ResponseDocument
import com.team.recordream.data.entity.remote.response.ResponseWrapper

interface DocumentDataSource {
    suspend fun getDocument(recordId: String): ResponseWrapper<ResponseDocument>

    suspend fun deleteDetailRecord(recordId: String): ResponseWrapper<Unit>

    suspend fun getDetailRecord(recordId: String): DetailRecordResponseDto
}
