package com.team.recordream.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.recordream.domain.model.UserRecord
import com.team.recordream.domain.repository.HomeRepository
import com.team.recordream.presentation.home.model.UserRecords
import com.team.recordream.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private val _resetViewPagerEvent = MutableLiveData<Unit>()
    val resetViewPagerEvent: LiveData<Unit> get() = _resetViewPagerEvent

    fun triggerResetViewPager() {
        _resetViewPagerEvent.value = Unit
    }

    private val _isRecordEmpty: MutableLiveData<Boolean> = MutableLiveData(true)
    val isRecordEmpty: LiveData<Boolean> get() = _isRecordEmpty

    private val _userName: MutableLiveData<UiState<String>> =
        MutableLiveData<UiState<String>>(UiState.Loading)
    val userName: LiveData<UiState<String>> get() = _userName

    private val _userRecords: MutableLiveData<List<UserRecords>> = MutableLiveData(listOf())
    val userRecords: LiveData<List<UserRecords>> get() = _userRecords

    fun updateHome() {
        viewModelScope.launch {
            _userName.value = UiState.Loading
            homeRepository.getHomeRecord()
                .onSuccess {
                    _userRecords.value = it.records.map { record -> record.toUiModel() }
                    _isRecordEmpty.value = userRecords.value.isNullOrEmpty()
                    _userName.value = UiState.Success(it.nickname)
                }
                .onFailure {
                    Log.d("12312344", it.message.toString())
                }
        }
    }

    private fun UserRecord.Record.toUiModel(): UserRecords = UserRecords(
        id = id,
        date = date,
        emotion = emotion,
        genre = genre,
        title = title,
        content = content,
        voice = voice,
    )
}
