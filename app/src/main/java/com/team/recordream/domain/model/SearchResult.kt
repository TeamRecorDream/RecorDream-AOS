package com.team.recordream.domain.model

data class SearchResult(
    val records: List<SearchedRecord>,
    val recordsCount: Int,
)
