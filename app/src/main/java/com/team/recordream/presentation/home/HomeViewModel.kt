package com.team.recordream.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.recordream.domain.model.UserRecord
import com.team.recordream.domain.repository.HomeRepository
import com.team.recordream.presentation.home.model.UserRecords
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private val _isRecordEmpty: MutableLiveData<Boolean> = MutableLiveData(true)
    val isRecordEmpty: LiveData<Boolean> get() = _isRecordEmpty

    private val _userName: MutableLiveData<String> = MutableLiveData("")
    val userName: LiveData<String> get() = _userName

    private val _userRecords: MutableLiveData<List<UserRecords>> = MutableLiveData(listOf())
    val userRecords: LiveData<List<UserRecords>> get() = _userRecords

    fun updateHome() {
        viewModelScope.launch {
            homeRepository.getHomeRecord()
                .onSuccess {
                    _userName.value = it.nickname
                    _userRecords.value = it.records.map { record -> record.toUiModel() }

                    if (!userRecords.value.isNullOrEmpty()) _isRecordEmpty.value = false
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
