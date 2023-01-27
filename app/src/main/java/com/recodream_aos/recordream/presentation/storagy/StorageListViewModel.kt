package com.recodream_aos.recordream.presentation.storagy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recodream_aos.recordream.domain.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageListViewModel @Inject constructor(
    private val storageRepository: StorageRepository
) : ViewModel() {

    init {
        initServer()
    }

    //    val cardInfo: LiveData<>
    private fun initServer() {
        viewModelScope.launch {
            storageRepository.getStorage(2)
        }
    }

}