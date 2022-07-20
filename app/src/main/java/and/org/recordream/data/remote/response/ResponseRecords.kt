package and.org.recordream.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseRecords(
    val records: List<Record>,
    @SerializedName("records_count")
    val recordTotal: Int
)