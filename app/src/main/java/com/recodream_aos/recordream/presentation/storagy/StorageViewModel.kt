package com.recodream_aos.recordream.presentation.storagy

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.data.entity.local.StorageEmotionData
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
    val storageList = storageEmotionList
    private val _storageRecordCount = MutableLiveData<Int>()
    val storageRecordCount: LiveData<Int> get() = _storageRecordCount

    private val _storageCheckList = MutableLiveData<Boolean>()
    val storageCheckList: LiveData<Boolean> get() = _storageCheckList

//    currentSelected = 0 기존꺼 는 false

    fun initServer(getEmotion: Int) {
        viewModelScope.launch {
            _storageRecords.value = storageRepository.getStorage(getEmotion)?.data?.records
            _storageRecordCount.value = storageRepository.getStorage(getEmotion)?.data?.recordsCount
//            Log.d(
//                "viewModel",
//                "initServer: ${storageRepository.getStorage(getEmotion)?.data?.records}"
//            )
        }
    }

    fun isCheckShow(show: Boolean) {
        _storageCheckList.value = show
        Log.d("storageViewModel", "isCheckShow: ${show}")
    }

    companion object {
        const val ALL = "전체"
        const val JOY = "기쁜"
        const val SAD = "슬픈"
        const val SCARY = "무서운"
        const val STRANGE = "이상한"
        const val SHY = "민망한"
        const val BLANK = "미설정"

        private val storageEmotionList = listOf(
            StorageEmotionData(
                feeling = R.drawable.emotion_all_selector,
                feelingText = ALL,
                isSelected = true
            ),
            StorageEmotionData(
                feeling = R.drawable.emotion_joy_selector,
                feelingText = JOY,
            ),
            StorageEmotionData(
                feeling = R.drawable.emotion_sad_selector,
                feelingText = SAD
            ),
            StorageEmotionData(
                feeling = R.drawable.emotion_scary_selector,
                feelingText = SCARY
            ),
            StorageEmotionData(
                feeling = R.drawable.emotion_strange_selector,
                feelingText = STRANGE
            ),
            StorageEmotionData(
                feeling = R.drawable.feeling_xs_shy,
                feelingText = SHY
            ),
            StorageEmotionData(
                feeling = R.drawable.emotion_blank_selector,
                feelingText = BLANK
            ),
        )
    }
}
