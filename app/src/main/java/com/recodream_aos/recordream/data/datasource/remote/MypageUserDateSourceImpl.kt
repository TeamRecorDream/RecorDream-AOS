package com.recodream_aos.recordream.data.datasource.remote

import android.util.Log
import com.recodream_aos.recordream.data.api.MypageService
import com.recodream_aos.recordream.data.entity.remote.request.RequestAlamToggle
import com.recodream_aos.recordream.data.entity.remote.request.RequestPushAlam
import com.recodream_aos.recordream.data.entity.remote.response.NoDataResponse
import com.recodream_aos.recordream.data.entity.remote.response.ResponseAlamToggle
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

    override suspend fun postPushAlam(alamTime: RequestPushAlam): NoDataResponse {
        Log.d("MypageUserDateSourceImpl", "postPushAlam: ${mypageService.postPushAlam(alamTime)}")
        Log.d("MypageUserDateSourceImpl", "안녕2")
        return mypageService.postPushAlam(alamTime)
    }

    override suspend fun patchAlamToggle(isActive: RequestAlamToggle): ResponseWrapper<ResponseAlamToggle> {
        Log.d("MypageUserDateSourceImpl", "patchAlamToggle: ${mypageService.patchToggle(isActive)}")
        Log.d("MypageUserDateSourceImpl", "안녕3")
        return mypageService.patchToggle(isActive)
    }

}
