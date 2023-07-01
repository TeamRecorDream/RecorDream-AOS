package com.recodream_aos.recordream.domain.model

data class SearchResult(
    val records: List<SearchedRecord>,
    val recordsCount: Int,
)
