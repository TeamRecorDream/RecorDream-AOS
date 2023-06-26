package com.recodream_aos.recordream.domain.repository

import com.recodream_aos.recordream.data.entity.remote.response.ResponseDocument
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper

interface DocumentRepository {
    suspend fun getDocument(recordId: String): ResponseWrapper<ResponseDocument>?
}
