package com.recodream_aos.recordream.data.repository

import com.recodream_aos.recordream.data.datasource.remote.DocumentDataSource
import com.recodream_aos.recordream.data.entity.remote.response.ResponseDocument
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import com.recodream_aos.recordream.domain.repository.DocumentRepository
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
                data = Unit
            )
        } catch (e: Exception) {
            return ResponseWrapper(
                status = 500,
                success = false,
                message = e.message ?: "Error occurred",
                data = null
            )
        }
    }
}
