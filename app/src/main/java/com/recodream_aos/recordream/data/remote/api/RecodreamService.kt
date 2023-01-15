package com.recodream_aos.recordream.data.remote.api

import com.recodream_aos.recordream.data.remote.response.storage.ResponseStore
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecodreamService {

    @GET("/record/storage")
    fun getSorage(
        @Query("filter") user: Int
    ): Call<ResponseStore>

}