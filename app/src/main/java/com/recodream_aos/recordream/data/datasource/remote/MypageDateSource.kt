package com.recodream_aos.recordream.data.datasource.remote

import com.recodream_aos.recordream.data.entity.remote.response.ResponseMypageUser
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper

interface MypageDateSource {
    suspend fun getUser(): ResponseWrapper<ResponseMypageUser>
}
