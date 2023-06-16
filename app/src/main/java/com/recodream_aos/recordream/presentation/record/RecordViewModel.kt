package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModel
import com.recodream_aos.recordream.presentation.record.uistate.Genre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecordViewModel : ViewModel() {
    private val _date: MutableStateFlow<String> =
        MutableStateFlow(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)))
    val date: StateFlow<String> get() = _date

    private val _time: MutableStateFlow<String> = MutableStateFlow(DEFAULT_TIME)
    val time: StateFlow<String> get() = _time

    private val _emotion: MutableStateFlow<Int?> = MutableStateFlow(null)
    val emotion: StateFlow<Int?> get() = _emotion

    private val _genre: MutableStateFlow<MutableList<Int>> = MutableStateFlow(mutableListOf())
    val genre: StateFlow<List<Int>> = _genre

    private val _genreEnabled: MutableStateFlow<List<Boolean>> =
        MutableStateFlow(List(10) { true })
    val genreEnabled: StateFlow<List<Boolean>> = _genreEnabled

    val title = MutableStateFlow(BLANK)
    val content = MutableStateFlow(BLANK)
    val note = MutableStateFlow(BLANK)
    var getRecordState = false

    fun updateSelectedEmotionId(emotionId: Int) {
        _emotion.value = emotionId
    }

    fun updateSelectedGenreId(genre: Genre) {
        if (_genre.value.contains(genre.genreId)) {
            _genre.value.remove(genre.genreId)
            _genreEnabled.value = List(10) { true }
            return
        }
        if (_genre.value.size < MAX_COUNT_OF_GENRE) {
            _genre.value.add(genre.genreId)
            if (_genre.value.size == MAX_COUNT_OF_GENRE) {
                _genreEnabled.value = List(10) { index ->
                    index + CORRECTION_VALUE in _genre.value
                }
            }
        }
    }

    fun updateDate() = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        _date.value = "$year-${(month + CORRECTION_VALUE).toStringOfDate()}-${day.toStringOfDate()}"
    }

    private fun Int.toStringOfDate(): String {
        if (this < TWO_DIGITS) return UNIT_TENS + this.toString()
        return this.toString()
    }

    companion object {
        private const val BLANK = ""
        private const val DEFAULT_TIME = "00:00"
        private const val DATE_PATTERN = "yyyy-MM-dd"
        private const val UNIT_TENS = "0"
        private const val CORRECTION_VALUE = 1
        private const val TWO_DIGITS = 10
        private const val MAX_COUNT_OF_GENRE = 3
    }
}
