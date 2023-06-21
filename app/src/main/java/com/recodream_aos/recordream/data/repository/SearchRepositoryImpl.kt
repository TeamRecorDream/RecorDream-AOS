package com.recodream_aos.recordream.data.repository

import com.recodream_aos.recordream.domain.model.Record
import com.recodream_aos.recordream.domain.repository.SearchRepository

// ktlint-disable package-name

class SearchRepositoryImpl() : SearchRepository {
    override suspend fun postSearch(keyword: String): Record {
        TODO("Not yet implemented")
    }
}
