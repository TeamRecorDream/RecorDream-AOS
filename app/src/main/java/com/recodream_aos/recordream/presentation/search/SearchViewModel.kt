package com.recodream_aos.recordream.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recodream_aos.recordream.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {
    val searchKeyword: MutableStateFlow<String> = MutableStateFlow(DEFAULT_VALUE_STRING)

    private val _resultCount: MutableStateFlow<Int> = MutableStateFlow(DEFAULT_VALUE_INT)
    val resultCount: StateFlow<Int> = _resultCount

    private val _isExistence: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isExistence: StateFlow<Boolean?> = _isExistence

    fun postSearch() {
        viewModelScope.launch {
            searchRepository.postSearch(searchKeyword.value)
        }
    }

    companion object {
        private const val DEFAULT_VALUE_STRING = ""
        private const val DEFAULT_VALUE_INT = 0
        private const val DEFAULT_VALUE_BOOLEAN = false
    }
}
