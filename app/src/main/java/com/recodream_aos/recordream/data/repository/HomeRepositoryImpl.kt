package com.recodream_aos.recordream.data.repository

import com.recodream_aos.recordream.data.datasource.remote.HomeDataSource
import com.recodream_aos.recordream.data.entity.remote.response.ResponseHome
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import com.recodream_aos.recordream.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource,
) : HomeRepository {
    override suspend fun getHomeRecord(): ResponseWrapper<ResponseHome>? {
        try {
            val response = homeDataSource.getHomeRecord()
            return response
        } catch (e: Exception) {
            return null
        }
    }
}
