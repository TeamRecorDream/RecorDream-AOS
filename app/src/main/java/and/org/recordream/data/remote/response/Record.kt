package and.org.recordream.data.remote.response

import com.google.gson.annotations.SerializedName

data class Record(
    @SerializedName("_id")
    val id: String,
    val date: String,
    @SerializedName("dream_color")
    val color: Int,
    val emotion: Int,
    val genre: List<Int>,
    val title: String
)