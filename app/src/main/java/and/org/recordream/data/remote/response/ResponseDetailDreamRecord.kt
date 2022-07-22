package and.org.recordream.data.remote.response

data class ResponseDetailDreamRecord(
    val _id: String?,
    val date: String?,
    val dream_color: Int?,
    val emotion: Int?,
    val genre: List<Int>,
    val content: String?,
    val note: String?,
    val title: String?,
    val voice: Voice?,
    val writer: String?
)