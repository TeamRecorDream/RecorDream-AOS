package com.team.recordream.domain.repository

import com.team.recordream.domain.model.DetailRecord

interface DocumentRepository {
    suspend fun deleteDetailRecord(recordId: String): Result<Unit>

    suspend fun getDetailRecord(recordId: String): Result<DetailRecord>
}
