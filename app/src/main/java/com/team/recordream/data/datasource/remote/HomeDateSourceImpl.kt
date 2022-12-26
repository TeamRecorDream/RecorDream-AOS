package com.team.recordream.data.datasource.remote

import com.team.recordream.data.api.HomeService
import com.team.recordream.data.entity.remote.response.ResponseHome
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import javax.inject.Inject

class HomeDateSourceImpl @Inject constructor(
    private val homeService: HomeService,
) : HomeDataSource {
    override suspend fun getHomeRecord(): ResponseWrapper<ResponseHome> {
        return homeService.getHomeRecord()
    }
}
