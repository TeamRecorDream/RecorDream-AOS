package and.org.recordream.data.remote.api

import and.org.recordream.data.remote.request.RequestWrite
import and.org.recordream.data.remote.response.ResponseWrapper
import and.org.recordream.data.remote.response.ResponseWrite
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface WriteService {

    @POST("record")
    fun postRecordDescription(
        @Header("userId") userId: Int = 1,
        @Body body: RequestWrite
    ): Call<ResponseWrapper<ResponseWrite>>

}