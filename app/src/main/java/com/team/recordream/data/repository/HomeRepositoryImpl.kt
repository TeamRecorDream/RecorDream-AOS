package com.team.recordream.data.repository

import com.team.recordream.data.datasource.remote.HomeDataSource
import com.team.recordream.domain.model.UserRecord
import com.team.recordream.domain.repository.HomeRepository
import com.team.recordream.mapper.toDomain
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource,
) : HomeRepository {
    override suspend fun getHomeRecord(): Result<UserRecord> =
        runCatching { homeDataSource.getHomeRecord().toDomain() }
}
