package and.org.recordream.data.local

data class Record(

    val _id: String,
    val date: String,
    val dream_color: Int,
    val emotion: Int,
    val genre: List<Int>,
    val title: String
)
