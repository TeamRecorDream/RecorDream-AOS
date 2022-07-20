package and.org.recordream.data.remote.api

import and.org.recordream.data.remote.response.ResponseDetailDreamRecord
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RecorDreamService {
    @GET("record/:{recordId}")
    fun getDetailRecord(
        @Path("recordId") recordId: String
    ): Call<ResponseDetailDreamRecord>
}