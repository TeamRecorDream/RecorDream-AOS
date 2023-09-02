package com.team.recordream.data.repository

import com.team.recordream.data.api.DocumentService
import com.team.recordream.domain.model.DetailRecord
import com.team.recordream.domain.repository.DocumentRepository
import com.team.recordream.mapper.toDomain
import javax.inject.Inject

class DocumentRepositoryImpl @Inject constructor(
    private val documentService: DocumentService,
) : DocumentRepository {

    override suspend fun deleteDetailRecord(recordId: String): Result<Unit> =
        runCatching { documentService.deleteDetailRecord(recordId) }

    override suspend fun getDetailRecord(recordId: String): Result<DetailRecord> =
        runCatching { documentService.getDetailRecord(recordId).toDomain() }
}
