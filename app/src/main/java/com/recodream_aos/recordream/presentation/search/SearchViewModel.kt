package com.recodream_aos.recordream.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.util.CustomResult.FAIL
import com.example.domain.util.CustomResult.SUCCESS
import com.recodream_aos.recordream.domain.model.SearchedRecord
import com.recodream_aos.recordream.domain.repository.SearchRepository
import com.recodream_aos.recordream.util.State
import com.recodream_aos.recordream.util.State.DISCONNECT
import com.recodream_aos.recordream.util.State.IDLE
import com.recodream_aos.recordream.util.State.INVALID
import com.recodream_aos.recordream.util.State.VALID
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

    private val _searchResult: MutableStateFlow<List<SearchedRecord>> = MutableStateFlow(
        mutableListOf(),
    )
    val searchResult: StateFlow<List<SearchedRecord>> = _searchResult

    private val _searchState: MutableStateFlow<State> = MutableStateFlow(IDLE)
    val searchState: StateFlow<State> = _searchState

    fun postSearch() {
        viewModelScope.launch {
            runCatching { searchRepository.postSearch(searchKeyword.value) }
                .onSuccess { result ->
                    when (result) {
                        is SUCCESS -> {
                            val temp = result.data
                            _resultCount.value = temp.recordsCount
                            _searchResult.value = temp.records
                            _searchState.value = VALID
                            _isExistence.value = true
                        }

                        is FAIL -> _searchState.value = INVALID
                    }
                }.onFailure { _searchState.value = DISCONNECT }
        }
    }

    companion object {
        private const val DEFAULT_VALUE_STRING = ""
        private const val DEFAULT_VALUE_INT = 0
        private const val DEFAULT_VALUE_BOOLEAN = false
    }
}
