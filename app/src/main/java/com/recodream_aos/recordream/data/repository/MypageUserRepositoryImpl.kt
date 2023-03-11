package com.recodream_aos.recordream.data.repository

import android.util.Log
import com.recodream_aos.recordream.data.datasource.remote.MypageDateSource
import com.recodream_aos.recordream.data.entity.remote.request.RequestAlamToggle
import com.recodream_aos.recordream.data.entity.remote.request.RequestPushAlam
import com.recodream_aos.recordream.data.entity.remote.response.NoDataResponse
import com.recodream_aos.recordream.data.entity.remote.response.ResponseAlamToggle
import com.recodream_aos.recordream.data.entity.remote.response.ResponseMypageUser
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import com.recodream_aos.recordream.domain.repository.MypageUserRepository
import javax.inject.Inject

class MypageUserRepositoryImpl @Inject constructor(
    private val mypageUserDataSource: MypageDateSource
) : MypageUserRepository {

    override suspend fun getUser(): ResponseWrapper<ResponseMypageUser>? {
        try {
            val response = mypageUserDataSource.getUser()
            return response
        } catch (e: Exception) {
            Log.d("MypageUserRepositoryImpl", "getUser: $e")
            return null
        }
    }

    override suspend fun postPushAlam(alamTime: RequestPushAlam): Result<NoDataResponse> {
        return kotlin.runCatching {
            mypageUserDataSource.postPushAlam(alamTime)
        }.onFailure {
            Log.d("MypageUserRepositoryImpl", "postPushAlam OnFail: ${it.message}")
            it.message
        }
    }

    override suspend fun patchAlamToggle(isActive: RequestAlamToggle): ResponseWrapper<ResponseAlamToggle>? {
        try {
            val response = mypageUserDataSource.patchAlamToggle(isActive)
            return response
        } catch (e: Exception) {
            Log.d("MypageUserRepositoryImpl", "patchAlamToggle: $e")
            return null
        }
    }


}
