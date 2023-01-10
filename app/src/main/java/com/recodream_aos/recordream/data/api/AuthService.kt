package com.recodream_aos.recordream.data.api // ktlint-disable package-name

import com.recodream_aos.recordream.data.entity.remote.request.RequestLogin
import com.recodream_aos.recordream.data.entity.remote.request.RequestNewToken
import com.recodream_aos.recordream.data.entity.remote.response.ResponseLogin
import com.recodream_aos.recordream.data.entity.remote.response.ResponseNewToken
import com.recodream_aos.recordream.data.entity.remote.response.ResponseWrapper
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun postLogin(
        @Body requestLogin: RequestLogin
    ): ResponseWrapper<ResponseLogin>

    @POST("/auth/login")
    suspend fun tryLogin(
        @Body requestLogin: RequestLogin
    ): ResponseWrapper<ResponseLogin>

    @POST("/auth/token")
    suspend fun postNewToken(
        @Body requestNewToken: RequestNewToken
    ): ResponseWrapper<ResponseNewToken>
}
