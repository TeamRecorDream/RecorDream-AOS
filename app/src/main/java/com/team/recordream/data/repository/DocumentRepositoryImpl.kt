package com.team.recordream.data.repository

import com.team.recordream.data.datasource.remote.DocumentDataSource
import com.team.recordream.data.entity.remote.response.ResponseDocument
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import com.team.recordream.domain.model.DetailRecord
import com.team.recordream.domain.repository.DocumentRepository
import com.team.recordream.mapper.toDomain
import javax.inject.Inject

class DocumentRepositoryImpl @Inject constructor(
    private val documentDataSource: DocumentDataSource,
) : DocumentRepository {

    override suspend fun getDocument(recordId: String): ResponseWrapper<ResponseDocument>? {
        try {
            val response = documentDataSource.getDocument(recordId)
            return response
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun deleteDetailRecord(recordId: String): ResponseWrapper<Unit> {
        try {
            documentDataSource.deleteDetailRecord(recordId)
            return ResponseWrapper(
                status = 200,
                success = true,
                message = "Deleted successfully",
                data = Unit,
            )
        } catch (e: Exception) {
            return ResponseWrapper(
                status = 500,
                success = false,
                message = e.message ?: "Error occurred",
                data = null,
            )
        }
    }

    override suspend fun getDetailRecord(recordId: String): Result<DetailRecord> {
        return runCatching { documentDataSource.getDetailRecord(recordId).toDomain() }
    }
}
