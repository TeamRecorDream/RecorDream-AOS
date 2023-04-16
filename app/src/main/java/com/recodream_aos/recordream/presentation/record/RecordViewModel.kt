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

    private val _genre: MutableStateFlow<MutableList<Int>> = MutableStateFlow(mutableListOf())
    val genre: StateFlow<List<Int>> get() = _genre

    private val _emotionId: MutableStateFlow<Int> = MutableStateFlow(NOTHING)
    val emotionId: StateFlow<Int> get() = _emotionId

    private val _emotionViewId: MutableStateFlow<Int> = MutableStateFlow(NOTHING)
    val emotionViewId: StateFlow<Int> get() = _emotionViewId

    val title = MutableStateFlow(BLANK)
    val content = MutableStateFlow(BLANK)
    val note = MutableStateFlow(BLANK)
    var getRecordState = false

    init {
        initLocalDate()
    }

//    fun saveRecordingMyDream() {
//        _emotion.value
//    } 서버연결메서드

    fun getSelectedGenreId(genreId: Int) {
        if (_genre.value.contains(genreId)) {
            _genre.value.remove(genreId)
            return
        }
        if (_genre.value.size == 3) {
            return
        }
        _genre.value.add(genreId)
    }

    fun getSelectedEmotionId(emotionID: Int) {
        if (_emotionId.value == emotionID) {
            _emotionId.value = NOTHING
            return
        }
        _emotionId.value = emotionID
    }

    fun getSelectedEmotionViewId(viewId: Int) {
        if (_emotionViewId.value == viewId) {
            _emotionViewId.value = NOTHING
            return
        }
        _emotionViewId.value = viewId
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
        private const val NOTHING = 0
        private const val ZERO = "0"
        private const val DEFAULT_TIME = "00:00"
        private const val ONE = 1
        private const val TEN = 10
        private const val BLANK = ""
        private const val DATE_PATTERN = "yyyy-MM-dd"
    }
}
