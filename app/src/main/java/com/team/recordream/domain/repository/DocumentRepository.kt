package com.team.recordream.domain.repository

import com.team.recordream.data.entity.remote.response.ResponseDocument
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import com.team.recordream.domain.model.DetailRecord

interface DocumentRepository {
    suspend fun getDocument(recordId: String): ResponseWrapper<ResponseDocument>?

    suspend fun deleteDetailRecord(recordId: String): ResponseWrapper<Unit>

    suspend fun getDetailRecord(recordId: String): Result<DetailRecord>
}
