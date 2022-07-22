package and.org.recordream.data.remote.api

import and.org.recordream.data.remote.response.ResponseDetailDreamRecord
import and.org.recordream.data.remote.response.ResponseEdit
import and.org.recordream.data.remote.response.ResponseWrapper
import retrofit2.Call
import retrofit2.http.PATCH
import retrofit2.http.Path

interface EditService {
    @PATCH("record/{recordId}")
    fun getEditRecord(
        @Path("recordId") recordId: String
    ): Call<ResponseWrapper<ResponseEdit>>
}