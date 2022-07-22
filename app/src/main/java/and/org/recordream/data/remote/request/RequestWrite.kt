package and.org.recordream.data.remote.request

import java.time.LocalDateTime

data class RequestWrite(
    val content: String,
    val date: LocalDateTime,
    val dream_color: Int,
    val emotion: Int,
    val genre: MutableSet<Int>,
    val note: String,
    val title: String,
    val voice: String,
    val writer: String
)