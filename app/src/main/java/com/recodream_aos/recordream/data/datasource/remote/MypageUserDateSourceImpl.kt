package com.recodream_aos.recordream.data.datasource.remote

import android.util.Log
import com.recodream_aos.recordream.data.api.MypageService
import com.recodream_aos.recordream.data.entity.remote.response.ResponseMypageUser
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import javax.inject.Inject

class MypageUserDateSourceImpl @Inject constructor(
    private val mypageService: MypageService
) : MypageDateSource {
    override suspend fun getUser(): ResponseWrapper<ResponseMypageUser> {
        Log.d("MypageUserDateSourceImpl", "getUser: ${mypageService.getUser()}")
        Log.d("MypageUserDateSourceImpl", "안녕")
        return mypageService.getUser()
    }
}
