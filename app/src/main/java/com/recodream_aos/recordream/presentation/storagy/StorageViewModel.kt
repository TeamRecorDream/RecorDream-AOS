package com.recodream_aos.recordream.presentation.storagy

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recodream_aos.recordream.data.entity.remote.response.ResponseStorage
import com.recodream_aos.recordream.domain.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val storageRepository: StorageRepository
) : ViewModel() {
    private val _storageRecords = MutableLiveData<List<ResponseStorage.Record>>()
    val storageRecords: LiveData<List<ResponseStorage.Record>>
        get() = _storageRecords
    private val _recordIsEmpty = MutableLiveData<Boolean>()
    val recordIsEmpty: LiveData<Boolean> get() = _recordIsEmpty

    init {
        initServer()
    }

    private fun initServer() {
        Log.d("세훈바보1", "initServer: ")
        viewModelScope.launch {
            _storageRecords.value = storageRepository.getStorage(2)?.data?.records
            Log.d("viewModel", "initServer: ${storageRepository.getStorage(2)?.data?.records}")
//            _viewRecordIsEmpty.value = "1234"
        }
    }

     fun checkEmpty() {
//        _recordIsEmpty.value = _storageRecords.value.isNullOrEmpty()
        if(_storageRecords.value.isNullOrEmpty()){
            _recordIsEmpty.value = false
        }
    }
}
