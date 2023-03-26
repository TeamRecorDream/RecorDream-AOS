package com.recodream_aos.recordream.data.repository

import android.util.Log
import com.recodream_aos.recordream.data.datasource.remote.MypageDateSource
import com.recodream_aos.recordream.data.entity.remote.response.ResponseMypageUser
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import com.recodream_aos.recordream.domain.repository.MypageUserRepository
import javax.inject.Inject

class MypageUserRepositoryImpl @Inject constructor(
    private val mypageUserDataSource: MypageDateSource
) : MypageUserRepository {

    override suspend fun getUser(): ResponseWrapper<ResponseMypageUser>? {
        //response값 오는 곳 근데 여기서 값이 안와 왜일까
        try {
            val response = mypageUserDataSource.getUser()
            return response
        } catch (e: Exception) {
            Log.d("MypageUserRepositoryImpl", "getUser: $e")
            return null
        }
    }
}
