package com.team.recordream.presentation.record // ktlint-disable package-name

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.recordream.domain.util.CustomResult.FAIL
import com.team.recordream.domain.util.CustomResult.SUCCESS
import com.team.recordream.domain.model.Record
import com.team.recordream.domain.repository.RecordRepository
import com.team.recordream.presentation.record.uistate.Genre
import com.team.recordream.util.State
import com.team.recordream.util.State.DISCONNECT
import com.team.recordream.util.State.IDLE
import com.team.recordream.util.State.INVALID
import com.team.recordream.util.State.VALID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
) : ViewModel() {
    val title: MutableStateFlow<String> = MutableStateFlow(DEFAULT_VALUE_STRING)

    private val _date: MutableStateFlow<String> =
        MutableStateFlow(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)))
    val date: StateFlow<String> get() = _date

    val content: MutableStateFlow<String?> = MutableStateFlow(DEFAULT_VALUE_NULL)

    private val _emotion: MutableStateFlow<Int?> = MutableStateFlow(DEFAULT_VALUE_NULL)
    val emotion: StateFlow<Int?> get() = _emotion

    private val _genre: MutableStateFlow<MutableList<Int>> = MutableStateFlow(mutableListOf())
    val genre: StateFlow<List<Int>> get() = _genre

    val note: MutableStateFlow<String?> = MutableStateFlow(DEFAULT_VALUE_NULL)

    private val _voiceId: MutableStateFlow<String?> = MutableStateFlow(DEFAULT_VALUE_NULL)
    val voiceId: StateFlow<String?> get() = _voiceId

    private val _recordingTime: MutableStateFlow<String> = MutableStateFlow(DEFAULT_TIME)
    val recordingTime: StateFlow<String> get() = _recordingTime

    private val _warningGenre: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val warningGenre: StateFlow<Boolean> get() = _warningGenre

    private val _genreEnabled: MutableStateFlow<List<Boolean>> =
        MutableStateFlow(List(ALL_GENRE) { true })
    val genreEnabled: StateFlow<List<Boolean>> get() = _genreEnabled

    private val _isSaveEnabled: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val isSaveEnabled: StateFlow<Boolean> get() = _isSaveEnabled

    private val _stateOfSavingRecord: MutableStateFlow<State> = MutableStateFlow(IDLE)
    val stateOfSavingRecord: StateFlow<State> get() = _stateOfSavingRecord

    fun postRecord() {
        viewModelScope.launch {
            runCatching { recordRepository.postRecord(getRecord()) }
                .onSuccess { result ->
                    when (result) {
                        is SUCCESS -> _stateOfSavingRecord.value = VALID
                        is FAIL -> _stateOfSavingRecord.value = INVALID
                    }
                }
                .onFailure { _stateOfSavingRecord.value = DISCONNECT }
        }
    }

    private fun getRecord(): Record = Record(
        title = title.value,
        date = date.value,
        content = content.value,
        emotion = emotion.value,
        genre = genre.value.ifEmpty { null },
        note = note.value,
        voice = voiceId.value,
    )

    fun updateId(id: String) {
        _voiceId.value = id
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
        private val DEFAULT_VALUE_NULL = null
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
