package com.team.recordream.data.datasource.remote

import com.team.recordream.data.api.DocumentService
import com.team.recordream.data.entity.remote.response.DetailRecordResponseDto
import com.team.recordream.data.entity.remote.response.ResponseDocument
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import javax.inject.Inject

class DocumentDataSourceImpl @Inject constructor(
    private val documentService: DocumentService,
) : DocumentDataSource {

    override suspend fun getDocument(recordId: String): ResponseWrapper<ResponseDocument> {
        return documentService.getDetailRecord(recordId)
    }

    override suspend fun deleteDetailRecord(recordId: String): ResponseWrapper<Unit> {
        try {
            documentService.deleteDetailRecord(recordId)
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

    override suspend fun getDetailRecord(recordId: String): DetailRecordResponseDto {
        return documentService.getDetailRecord2(recordId)
    }
}
