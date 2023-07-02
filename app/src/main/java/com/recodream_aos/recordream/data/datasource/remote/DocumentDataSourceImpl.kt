package com.recodream_aos.recordream.data.datasource.remote

import com.recodream_aos.recordream.data.api.DocumentService
import com.recodream_aos.recordream.data.entity.remote.response.ResponseDocument
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import javax.inject.Inject

class DocumentDataSourceImpl @Inject constructor(
    private val documentService: DocumentService,
) : DocumentDataSource {

    override suspend fun getDocument(recordId: String): ResponseWrapper<ResponseDocument> {
        return documentService.getDetailRecord(recordId)
    }

    override suspend fun deleteDetailRecord(recordId: String) {
        TODO("Not yet implemented")
    }
}
