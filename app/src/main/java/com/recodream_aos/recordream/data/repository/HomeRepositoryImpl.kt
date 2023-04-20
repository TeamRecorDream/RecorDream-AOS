package com.recodream_aos.recordream.data.repository

import android.util.Log
import com.recodream_aos.recordream.data.datasource.remote.HomeDataSource
import com.recodream_aos.recordream.data.entity.remote.response.ResponseHome
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import com.recodream_aos.recordream.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource
) : HomeRepository {
    override suspend fun getHomeRecord(): ResponseWrapper<ResponseHome>? {
        try {
            val response = homeDataSource.getHomeRecord()
            Log.d("babo", "getHomeRecord: $response")
            return response
        } catch (e: Exception) {
            Log.d("babo1", "getHomeRecord: 왜 null임 $e")
            return null
        }
    }
}
