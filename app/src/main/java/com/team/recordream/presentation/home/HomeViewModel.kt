package com.team.recordream.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.recordream.data.entity.remote.response.ResponseHome
import com.team.recordream.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private val _homeRecords = MutableLiveData<List<ResponseHome.Record>>()
    val homeRecords: LiveData<List<ResponseHome.Record>>
        get() = _homeRecords

    private val _responses = MutableLiveData<ResponseHome>()
    val responses: LiveData<ResponseHome>
        get() = _responses

    val userName = MutableLiveData<String>()
    fun initServer() {
        viewModelScope.launch { _homeRecords.value = homeRepository.getHomeRecord()?.data?.records }
    }

    fun getUser() {
        viewModelScope.launch {
            userName.value = homeRepository.getHomeRecord()?.data?.nickname
        }
    }
}
