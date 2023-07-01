package com.recodream_aos.recordream.data.repository

import com.recodream_aos.recordream.data.datasource.remote.DocumentDataSource
import com.recodream_aos.recordream.data.entity.remote.response.ResponseDocument
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import com.recodream_aos.recordream.domain.repository.DocumentRepository
import javax.inject.Inject

class DocumentRepositoryImpl @Inject constructor(
    private val documentDataSource: DocumentDataSource
) : DocumentRepository {

    override suspend fun getDocument(recordId: String): ResponseWrapper<ResponseDocument>? {
        // todo response 값이 오는 곳
        try {
            val response = documentDataSource.getDocument(recordId)
            return response
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun deleteDetailRecord(recordId: String) {
        TODO("Not yet implemented")
    }
}
