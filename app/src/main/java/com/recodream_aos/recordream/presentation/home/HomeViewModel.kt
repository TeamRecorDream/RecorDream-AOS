package com.recodream_aos.recordream.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recodream_aos.recordream.data.entity.remote.response.ResponseHome
import com.recodream_aos.recordream.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val _homeRecords = MutableLiveData<List<ResponseHome.Record>>()
    val homeRecords: LiveData<List<ResponseHome.Record>>
        get() = _homeRecords

    fun initServer() {
        viewModelScope.launch { _homeRecords.value = homeRepository.getHomeRecord()?.data?.records }
    }
}
