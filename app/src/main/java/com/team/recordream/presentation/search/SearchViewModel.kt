package com.team.recordream.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.recordream.domain.model.SearchResult
import com.team.recordream.domain.model.SearchedRecord
import com.team.recordream.domain.repository.SearchRepository
import com.team.recordream.domain.util.CustomResult.FAIL
import com.team.recordream.domain.util.CustomResult.SUCCESS
import com.team.recordream.presentation.search.uistate.SearchedRecordUiState
import com.team.recordream.util.State
import com.team.recordream.util.State.DISCONNECT
import com.team.recordream.util.State.IDLE
import com.team.recordream.util.State.INVALID
import com.team.recordream.util.State.VALID
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
    val resultCount: StateFlow<Int> get() = _resultCount

    private val _messageVisible: MutableStateFlow<Boolean> = MutableStateFlow(INVISIBLE)
    val messageVisible: StateFlow<Boolean> get() = _messageVisible

    private val _searchResultVisible: MutableStateFlow<Boolean> = MutableStateFlow(INVISIBLE)
    val searchResultVisible: StateFlow<Boolean> get() = _searchResultVisible

    private val _searchResult: MutableStateFlow<List<SearchedRecordUiState>> = MutableStateFlow(
        mutableListOf(),
    )
    val searchResult: StateFlow<List<SearchedRecordUiState>> get() = _searchResult

    private val _searchState: MutableStateFlow<State> = MutableStateFlow(IDLE)
    val searchState: StateFlow<State> get() = _searchState

    fun postSearch() {
        viewModelScope.launch {
            runCatching {
                if (searchKeyword.value.isNotEmpty()) searchRepository.postSearch(searchKeyword.value) else return@launch
                // 이후 검색 최소 글자 수 관련된 로직 추가
            }.onSuccess { result ->
                when (result) {
                    is SUCCESS -> onSuccessInitServer(result)
                    is FAIL -> _searchState.value = INVALID
                }
            }.onFailure { _searchState.value = DISCONNECT }
        }
    }

    private fun onSuccessInitServer(result: SUCCESS<SearchResult>) {
        updateValueFromServer(result)
        when (result.data.recordsCount == EMPTY) {
            true -> updateVisibilityOnEmpty()
            false -> updateVisibilityOnOccupied()
        }
    }

    private fun updateValueFromServer(result: SUCCESS<SearchResult>) {
        _resultCount.value = result.data.recordsCount
        _searchResult.value = result.data.records.map { it.toUiState() }
        _searchState.value = VALID
    }

    private fun updateVisibilityOnOccupied() {
        _messageVisible.value = INVISIBLE
        _searchResultVisible.value = VISIBLE
    }

    private fun updateVisibilityOnEmpty() {
        _messageVisible.value = VISIBLE
        _searchResultVisible.value = INVISIBLE
    }

    private fun SearchedRecord.toUiState(): SearchedRecordUiState = SearchedRecordUiState(
        _id = this._id,
        date = this.date,
        emotion = this.emotion,
        genre = this.genre,
        title = this.title,
    )

    companion object {
        private const val DEFAULT_VALUE_STRING = ""
        private const val DEFAULT_VALUE_INT = 0
        private const val INVISIBLE = false
        private const val VISIBLE = true
        private const val EMPTY = 0
    }
}
