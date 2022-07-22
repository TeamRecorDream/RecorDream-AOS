package and.org.recordream.data.remote.response

data class ResponseEdit(
    val content: String,
    val date: String,
    val dream_color: Int,
    val emotion: Int,
    val genre: List<Int>,
    val note: String,
    val title: String
)