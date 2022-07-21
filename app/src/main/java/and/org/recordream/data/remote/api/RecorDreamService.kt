package and.org.recordream.data.remote.api

import and.org.recordream.data.remote.response.ResponseDetailDreamRecord
import and.org.recordream.data.remote.response.ResponseWrapper
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface RecorDreamService {
    @GET("record/{recordId}")
    fun getDetailRecord(
        @Path("recordId") recordId: String
    ): Call<ResponseWrapper<ResponseDetailDreamRecord>>

    @DELETE("record/{recordId}")
    fun deleteDetailRecord(
        @Path("recordId") recordId: String = "62d82d0cbb7aaf28837aa30a"
    ): Call<ResponseWrapper<Response>>
}