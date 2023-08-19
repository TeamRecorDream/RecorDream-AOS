package com.team.recordream.presentation.storagy

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.recordream.R
import com.team.recordream.data.entity.local.StorageEmotionData
import com.team.recordream.data.entity.remote.response.ResponseStorage
import com.team.recordream.domain.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
) : ViewModel() {
    private val _storageRecords = MutableLiveData<List<ResponseStorage.Record>>()
    val storageRecords: LiveData<List<ResponseStorage.Record>>
        get() = _storageRecords
    val storageList = storageEmotionList

    var storageEmotion = MutableLiveData<StorageEmotionData>()

    private val _storageRecordCount = MutableLiveData<Int>()
    val storageRecordCount: LiveData<Int> get() = _storageRecordCount

    private val _storageCheckList = MutableLiveData<Boolean>()
    val storageCheckList: LiveData<Boolean> get() = _storageCheckList
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    fun initServer(getEmotion: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                storageRepository.getStorage(getEmotion)?.data
            }.onSuccess {
                _storageRecords.value = it?.records
                _storageRecordCount.value = it?.recordsCount
            }.onFailure {
                Timber.d("${it.message}")
                errorMessage.value = it.message
            }
        }
    }

    fun isCheckShow(show: Boolean) {
        _storageCheckList.value = show
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
                selectedImage = R.drawable.feeling_xs_all,
                unSelectedImage = R.drawable.feeling_xs_all_off,
                feelingText = ALL,
                isSelected = true,
            ),
            StorageEmotionData(
                selectedImage = R.drawable.feeling_xs_joy,
                unSelectedImage = R.drawable.feeling_xs_joy_off,
                feelingText = JOY,
            ),
            StorageEmotionData(
                selectedImage = R.drawable.feeling_xs_sad,
                unSelectedImage = R.drawable.feeling_xs_sad_off,
                feelingText = SAD,
            ),
            StorageEmotionData(
                selectedImage = R.drawable.feeling_xs_scary,
                unSelectedImage = R.drawable.feeling_xs_scary_off,
                feelingText = SCARY,
            ),
            StorageEmotionData(
                selectedImage = R.drawable.feeling_xs_strange,
                unSelectedImage = R.drawable.feeling_xs_strange_off,
                feelingText = STRANGE,
            ),
            StorageEmotionData(
                selectedImage = R.drawable.feeling_xs_shy,
                unSelectedImage = R.drawable.feeling_xs_shy_off,
                feelingText = SHY,
            ),
            StorageEmotionData(
                selectedImage = R.drawable.feeling_xs_blank,
                unSelectedImage = R.drawable.feeling_xs_blank_off,
                feelingText = BLANK,
            ),
        )
    }
}
