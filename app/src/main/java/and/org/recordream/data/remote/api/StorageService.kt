package and.org.recordream.data.remote.api

import and.org.recordream.data.remote.response.Record
import and.org.recordream.data.remote.response.ResponseWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface StorageService {

    @GET("record/storage?filter={num}")
    fun getMyRecord(
        @Path("num") num: Int,
        @Header("userId") userId: Int = 1
    ): Call<ResponseWrapper<List<Record>>>
}