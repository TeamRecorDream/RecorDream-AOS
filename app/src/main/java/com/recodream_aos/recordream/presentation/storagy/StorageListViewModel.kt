package com.recodream_aos.recordream.presentation.storagy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recodream_aos.recordream.data.remote.response.storage.StoreCard
import com.recodream_aos.recordream.domain.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageListViewModel @Inject constructor(
    //ㅠㅠ 알빠냐?
    private val storageRepository: StorageRepository
) : ViewModel() {


    private val _cardInfo = MutableLiveData<StoreCard>()

    init {
        initServer()
    }

    //    val cardInfo: LiveData<>
    private fun initServer() {
        viewModelScope.launch {
            storageRepository.getStorage()
        }
    }

}