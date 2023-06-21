package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recodream_aos.recordream.presentation.record.uistate.Genre
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecordViewModel : ViewModel() {
    val title: MutableStateFlow<String> = MutableStateFlow(DEFAULT_VALUE_STRING)
    val content = MutableStateFlow(DEFAULT_VALUE_STRING)
    val note = MutableStateFlow(DEFAULT_VALUE_STRING)

    private val _id: MutableStateFlow<String> = MutableStateFlow(DEFAULT_VALUE_STRING)
    val id: StateFlow<String> = _id

    private val _date: MutableStateFlow<String> =
        MutableStateFlow(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)))
    val date: StateFlow<String> get() = _date

    private val _recordingTime: MutableStateFlow<String> = MutableStateFlow(DEFAULT_TIME)
    val recordingTime: StateFlow<String> get() = _recordingTime

    private val _emotion: MutableStateFlow<Int?> = MutableStateFlow(null)
    val emotion: StateFlow<Int?> get() = _emotion

    private val _genre: MutableStateFlow<MutableList<Int>> = MutableStateFlow(mutableListOf())
    val genre: StateFlow<List<Int>> = _genre

    private val _genreEnabled: MutableStateFlow<List<Boolean>> =
        MutableStateFlow(List(ALL_GENRE) { true })
    val genreEnabled: StateFlow<List<Boolean>> = _genreEnabled

    private val _warningGenre: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val warningGenre: StateFlow<Boolean> = _warningGenre

    private val _isSaveEnabled: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val isSaveEnabled: StateFlow<Boolean> = _isSaveEnabled

    fun postRecord(): String {
        viewModelScope.launch {

        }
        return ""
    }

    fun updateId(id: String) {
        _id.value = id
    }

    fun updateSaveButtonEnabled() {
        _isSaveEnabled.value = title.value.isNotEmpty() && title.value.first() != BLANK
    }

    fun updateRecordingTime(recordingTime: String) {
        _recordingTime.value = recordingTime
    }

    fun updateDate() = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        _date.value = "$year-${(month + CORRECTION_VALUE).toStringOfDate()}-${day.toStringOfDate()}"
    }

    fun updateSelectedEmotionId(emotionId: Int) {
        if (emotion.value == emotionId) {
            _emotion.value = null
            return
        }
        _emotion.value = emotionId
    }

    fun updateSelectedGenreId(genre: Genre) {
        val isContained = _genre.value.contains(genre.genreId)
        val isReachedMaxCount = _genre.value.size == MAX_COUNT_OF_GENRE

        when {
            isContained -> handleContainedGenre(genre)
            !isReachedMaxCount -> handleNonContainedGenre(genre)
        }
    }

    private fun handleNonContainedGenre(genre: Genre) {
        _genre.value.add(genre.genreId)
        if (_genre.value.size == MAX_COUNT_OF_GENRE) handleIfReachMaxCount(NON_CONTAINED)
    }

    private fun handleContainedGenre(genre: Genre) {
        if (_genre.value.size == MAX_COUNT_OF_GENRE) handleIfReachMaxCount(CONTAINED)
        _genre.value.remove(genre.genreId)
    }

    private fun handleIfReachMaxCount(isContained: Boolean) {
        viewModelScope.launch {
            when (isContained) {
                true -> {
                    _genreEnabled.value = List(ALL_GENRE) { true }
                    _warningGenre.value = HIDE
                }

                false -> {
                    _genreEnabled.value = List(ALL_GENRE) { it + CORRECTION_VALUE in _genre.value }
                    _warningGenre.value = SHOW
                    delay(TWO_SECONDS)
                    _warningGenre.value = HIDE
                }
            }
        }
    }

    private fun Int.toStringOfDate(): String {
        if (this < TWO_DIGITS) return UNIT_TENS + this.toString()
        return this.toString()
    }

    companion object {
        private const val BLANK = ' '
        private const val DEFAULT_VALUE_STRING = ""
        private const val DEFAULT_TIME = "00:00"
        private const val DATE_PATTERN = "yyyy-MM-dd"
        private const val UNIT_TENS = "0"
        private const val CORRECTION_VALUE = 1
        private const val TWO_DIGITS = 10
        private const val ALL_GENRE = 10
        private const val TWO_SECONDS: Long = 2000
        private const val MAX_COUNT_OF_GENRE = 3
        private const val CONTAINED = true
        private const val NON_CONTAINED = false
        private const val SHOW = true
        private const val HIDE = false
    }
}
