package com.recodream_aos.recordream.presentation.storagy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.annotations.SerializedName
import com.recodream_aos.recordream.data.remote.api.RecodreamService
import com.recodream_aos.recordream.data.remote.response.storage.StoreCard
import kotlinx.coroutines.launch

class StorageListViewModel : ViewModel() {
    //    val mockHomelist = listOf<StoreCard>(
//        StoreCard(id = 1, day = "dfd", description = "ddf"),
//        StoreCard(id = 2, day = "dfd", description = "ddf"),
//        StoreCard(id = 3, day = "dfd", description = "ddf"),
//        StoreCard(id = 4, day = "dfd", description = "ddf")
//    )

    val id: String,
    val date: String,
    val emotion: Int,
    val genre: List<Int>,

    @SerializedName("title")
    val description: String

    private val _cardInfo = MutableLiveData<StoreCard>()
//    val cardInfo: LiveData<>

    fun initServer() {
        viewModelScope.launch {
            kotlin.runCatching {
                RecodreamService.getSorage(

                )
            }
        }
    }


}