package com.team.recordream.data.repository

import com.team.recordream.data.datasource.remote.HomeDataSource
import com.team.recordream.data.entity.remote.response.ResponseHome
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import com.team.recordream.domain.repository.HomeRepository
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
