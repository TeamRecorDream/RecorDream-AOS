package and.org.recordream.data.remote.request

import com.google.gson.annotations.SerializedName

data class RequestMypagePutTime(
    @SerializedName("fcm_token")
    val token: String,
    val time: String,
)
