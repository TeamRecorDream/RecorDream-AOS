package com.recodream_aos.recordream.data.remote.api // ktlint-disable package-name

import com.recodream_aos.recordream.data.remote.request.RequestLogin
import com.recodream_aos.recordream.data.remote.response.ResponseLogin
import com.recodream_aos.recordream.data.remote.response.ResponseWrapper
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/auth/login")
    suspend fun postLogin(
        @Body requestLogin: RequestLogin
    ): ResponseWrapper<ResponseLogin>
}
