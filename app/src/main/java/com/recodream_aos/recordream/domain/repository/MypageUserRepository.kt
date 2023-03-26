package com.recodream_aos.recordream.domain.repository

import com.recodream_aos.recordream.data.entity.remote.response.ResponseMypageUser
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper

interface MypageUserRepository {

    suspend fun getUser(): ResponseWrapper<ResponseMypageUser>?
}