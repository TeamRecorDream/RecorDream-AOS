package and.org.recordream.presentation.home

data class ResponseHomeItems(
    val nickname: String,
    val records: List<Records>
) {
    data class Records(
        val dream_color: Int,
        val emotion: Int,
        val date: String,
        val title: String,
        val genre: List<Int>
    )
}