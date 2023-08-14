package com.team.recordream.data.repository

import com.team.recordream.data.datasource.remote.MypageDateSource
import com.team.recordream.data.entity.remote.request.RequestAlamToggle
import com.team.recordream.data.entity.remote.request.RequestNickName
import com.team.recordream.data.entity.remote.request.RequestPushAlam
import com.team.recordream.data.entity.remote.response.NoDataResponse
import com.team.recordream.data.entity.remote.response.ResponseAlamToggle
import com.team.recordream.data.entity.remote.response.ResponseMypageUser
import com.team.recordream.data.entity.remote.response.ResponseWrapper
import com.team.recordream.domain.repository.MypageUserRepository
import timber.log.Timber
import javax.inject.Inject

class MypageUserRepositoryImpl @Inject constructor(
    private val mypageUserDataSource: MypageDateSource,
) : MypageUserRepository {

    override suspend fun getUser(): ResponseWrapper<ResponseMypageUser>? {
        return try {
            val response = mypageUserDataSource.getUser()
            response
        } catch (e: Exception) {
            Timber.tag("MypageUserRepositoryImpl").d("getUser: " + e)
            null
        }
    }

    override suspend fun postPushAlam(alamTime: RequestPushAlam): Result<NoDataResponse> {
        return kotlin.runCatching {
            mypageUserDataSource.postPushAlam(alamTime)
        }.onFailure {
            it.message
        }
    }

    override suspend fun patchAlamToggle(isActive: RequestAlamToggle): ResponseWrapper<ResponseAlamToggle>? {
        try {
            val response = mypageUserDataSource.patchAlamToggle(isActive)
            return response
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun putNickName(nickName: RequestNickName): Result<NoDataResponse> {
        return kotlin.runCatching {
            mypageUserDataSource.putUserName(nickName)
        }.onFailure {
            Timber.tag("MypageUserRepositoryImpl").d("postPushAlam OnFail: %s", it.message)
            it.message
        }
    }
}
