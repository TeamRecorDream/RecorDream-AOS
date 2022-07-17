package and.org.recordream.data.remote.response

data class ResponseRecords(
    val records: List<Record>,
    val records_count: Int
)

data class Record(
    val _id: String,
    val date: String,
    val dream_color: Int,
    val emotion: Int,
    val genre: List<Int>,
    val title: String
)
