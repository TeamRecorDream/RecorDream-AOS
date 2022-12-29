package com.recodream_aos.recordream.presentation.storagy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.recodream_aos.recordream.data.remote.response.storage.StoreCard

class StorageListViewModel : ViewModel() {
    //    val mockHomelist = listOf<StoreCard>(
//        StoreCard(id = 1, day = "dfd", description = "ddf"),
//        StoreCard(id = 2, day = "dfd", description = "ddf"),
//        StoreCard(id = 3, day = "dfd", description = "ddf"),
//        StoreCard(id = 4, day = "dfd", description = "ddf")
//    )
    private val _cardInfo = MutableLiveData<StoreCard>()
//    val cardInfo: LiveData<>

    fun initServer() {

    }

}