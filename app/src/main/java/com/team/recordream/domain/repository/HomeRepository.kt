package com.team.recordream.domain.repository

import com.team.recordream.domain.model.UserRecord

interface HomeRepository {
    suspend fun getHomeRecord(): Result<UserRecord>
}
