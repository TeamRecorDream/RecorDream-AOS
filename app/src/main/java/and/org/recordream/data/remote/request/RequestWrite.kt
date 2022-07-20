package and.org.recordream.data.remote.request

data class RequestWrite(
    val content: String,
    val date: String,
    val dream_color: Int,
    val emotion: Int,
    val genre: List<Int>,
    val note: String,
    val title: String,
    val voice: String,
    val writer: String
)