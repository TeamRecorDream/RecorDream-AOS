package com.recodream_aos.recordream.data.datasource.remote

import com.recodream_aos.recordream.data.entity.remote.response.ResponseDocument
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper

interface DocumentDataSource {
    suspend fun getDocument(recordId: String): ResponseWrapper<ResponseDocument>
}
