package com.recodream_aos.recordream.presentation.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class SearchViewModel : ViewModel() {
    private val _searchKeyword: MutableStateFlow<String> = MutableStateFlow(DEFAULT_VALUE_STRING)
    val searchKeyword: StateFlow<String> = _searchKeyword

    private val _resultCount: MutableStateFlow<Int> = MutableStateFlow(DEFAULT_VALUE_INT)
    val resultCount: StateFlow<Int> = _resultCount

    private val _isExistence: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isExistence: StateFlow<Boolean?> = _isExistence

    companion object {
        private const val DEFAULT_VALUE_STRING = ""
        private const val DEFAULT_VALUE_INT = 0
        private const val DEFAULT_VALUE_BOOLEAN = false
    }
}
