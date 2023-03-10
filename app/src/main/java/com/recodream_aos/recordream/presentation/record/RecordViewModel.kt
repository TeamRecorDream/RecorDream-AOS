package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecordViewModel : ViewModel() {
    private var _date = MutableStateFlow(BLANK)
    val date: StateFlow<String> get() = _date

    private var _time = MutableStateFlow(DEFAULT_TIME)
    val time: StateFlow<String> get() = _time

    val isJoyButtonChecked = MutableStateFlow<Boolean>(false)
    val isSadButtonChecked = MutableStateFlow<Boolean>(false)
    val isScaryButtonChecked = MutableStateFlow<Boolean>(false)
    val isStrangeButtonChecked = MutableStateFlow<Boolean>(false)
    val isShyButtonChecked = MutableStateFlow<Boolean>(false)

    var emotion = MutableStateFlow(0)
    val title = MutableStateFlow(BLANK)
    val content = MutableStateFlow(BLANK)
    val note = MutableStateFlow(BLANK)
    var getRecordState = false

    init {
        initLocalDate()
    }

//    fun saveRecordingMyDream() {
//        emotion.value
//    } 서버연결메서드

    fun isEmotionSelected() {
        isJoyButtonChecked.value = emotion.value == Emotion.JOY.emotionID
        isSadButtonChecked.value = emotion.value == Emotion.SAD.emotionID
        isShyButtonChecked.value = emotion.value == Emotion.SHY.emotionID
        isScaryButtonChecked.value = emotion.value == Emotion.SCARY.emotionID
        isStrangeButtonChecked.value = emotion.value == Emotion.STRANGE.emotionID
    }

    fun initDate() = DatePickerDialog.OnDateSetListener { view, year, month, day ->
        _date.value = "$year-${modifyDateUnits(month + ONE)}-${modifyDateUnits(day)}"
    }

    private fun initLocalDate() {
        _date.value = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN))
    }

    private fun modifyDateUnits(month: Int): String {
        if (month < TEN) return ZERO + month.toString()
        return month.toString()
    }

    companion object {
        private const val ZERO = "0"
        private const val DEFAULT_TIME = "00:00"
        private const val ONE = 1
        private const val TEN = 10
        private const val BLANK = ""
        private const val DATE_PATTERN = "yyyy-MM-dd"
    }
}
