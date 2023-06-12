package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import android.app.DatePickerDialog
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecordViewModel : ViewModel() {
    private var _date =
        MutableStateFlow(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)))
    val date: StateFlow<String> get() = _date

    private var _time = MutableStateFlow(DEFAULT_TIME)
    val time: StateFlow<String> get() = _time

    private var _emotion = MutableStateFlow<Int?>(null)
    val emotion: StateFlow<Int?> get() = _emotion

    val genre: MutableStateFlow<MutableList<Int>> = MutableStateFlow(mutableListOf())

    // var emotion = MutableStateFlow(0)
    val title = MutableStateFlow(BLANK)
    val content = MutableStateFlow(BLANK)
    val note = MutableStateFlow(BLANK)
    var getRecordState = false

//    fun saveRecordingMyDream() {
//        emotion.value
//    } 서버연결메서드

    fun fetchSelectedEmotionId(emotionId: Int) {
        _emotion.value = emotionId
    }

    fun getSelectedGenreId(genreId: Int) {
        if (genre.value.contains(genreId)) {
            genre.value.remove(genreId)
            return
        }
        if (genre.value.size == 3) {
            return
        }
        genre.value.add(genreId)
        Log.d("listlist", "${genre.value}")
    }

    fun initDate() = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        _date.value = "$year-${modifyDateUnits(month + CORRECTION_VALUE)}-${modifyDateUnits(day)}"
    }

    private fun modifyDateUnits(month: Int): String {
        if (month < TWO_DIGITS) return (CORRECTION_VALUE + month).toString()
        return month.toString()
    }

    companion object {
        private const val DEFAULT_TIME = "00:00"
        private const val CORRECTION_VALUE = 1
        private const val TWO_DIGITS = 10
        private const val BLANK = ""
        private const val DATE_PATTERN = "yyyy-MM-dd"
    }
}
