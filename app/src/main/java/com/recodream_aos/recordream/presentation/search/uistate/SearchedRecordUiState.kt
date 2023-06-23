package com.recodream_aos.recordream.presentation.search.uistate

data class SearchedRecordUiState(
    val _id: String,
    val date: String,
    val emotion: Int,
    val genre: List<Int>,
    val title: String,
) {
    fun getFormattedDate(): String = Regex(PATTERN).replace(date, BLANK).trim()

    companion object {
        private const val PATTERN = "\\(|\\)"
        private const val BLANK = ""
    }
}
