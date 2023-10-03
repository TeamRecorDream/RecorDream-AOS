package com.team.recordream.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.recordream.R
import com.team.recordream.domain.repository.DocumentRepository
import com.team.recordream.presentation.record.uistate.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.timer

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val documentRepository: DocumentRepository,
) : ViewModel() {
    var recordId: String = ""
        private set

    private val _recordingFilePath: MutableStateFlow<String?> = MutableStateFlow(null)
    val recordingFilePath: StateFlow<String?> get() = _recordingFilePath

    private val _background: MutableStateFlow<Int> = MutableStateFlow(0)
    val background: StateFlow<Int> get() = _background

    private val _icon: MutableStateFlow<Int> = MutableStateFlow(0)
    val icon: StateFlow<Int> get() = _icon

    private val _date: MutableStateFlow<String> = MutableStateFlow("")
    val date: StateFlow<String> get() = _date

    private val _title: MutableStateFlow<String> = MutableStateFlow("")
    val title: StateFlow<String> get() = _title

    private val _tags: MutableStateFlow<List<Genre>> = MutableStateFlow(listOf())
    val tags: StateFlow<List<Genre>> get() = _tags

    private val _isRemoved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRemoved: StateFlow<Boolean> get() = _isRemoved
    // ?

    // 리팩터링 분리
    private val _note: MutableStateFlow<String> = MutableStateFlow("")
    val note: StateFlow<String> get() = _note

    // 리팩터링 분리
    private val _content: MutableStateFlow<String> =
        MutableStateFlow<String>("")
    val content: StateFlow<String> get() = _content

    private val _isRecorded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRecorded: StateFlow<Boolean> get() = _isRecorded

    // 리팩터링 분리
    private val _isPlayed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isPlayed: StateFlow<Boolean> get() = _isPlayed

    private val _recordingTime: MutableStateFlow<Int> = MutableStateFlow(0)
    val recordingTime: StateFlow<Int> get() = _recordingTime

    private val _playTime: MutableStateFlow<Int> = MutableStateFlow(0)
    val playTime: StateFlow<Int> get() = _playTime

    private val _progressRate: MutableStateFlow<Int> = MutableStateFlow(0)
    val progressRate: StateFlow<Int> get() = _progressRate

    private val _state: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState> get() = _state

    private var recordingTimer: Timer? = null
    private var progressTimer: Timer? = null

    fun updateDetailRecord(id: String) {
        recordId = id
        viewModelScope.launch {
            documentRepository.getDetailRecord(recordId)
                .onSuccess {
                    _background.value = Emotion.findBackgroundById(it.emotion ?: 0)
                    _icon.value = Emotion.findIconById(it.emotion ?: 0)
                    _date.value = it.date.replace(Regex("[()]"), "")
                    _title.value = it.title
                    _note.value = it.note ?: ""
                    _content.value = it.content ?: ""
                    findTagByGenreId(it.genre)
                    _isRecorded.value = it.voice != null
                    if (it.voice != null) _recordingFilePath.value = it.voice.url
                    _state.value = ViewState.Success
                }
                .onFailure {
                    Log.d("123123-DetailViewModel", it.message.toString())
                }
        }
    }

    fun updateRecordDeleted() {
        viewModelScope.launch {
            documentRepository.deleteDetailRecord(recordId)
                .onSuccess {
                    _isRemoved.value = true
                }
                .onFailure {
                    Log.d("123123-DetailViewModel", it.message.toString())
                }
        }
    }

    fun updateRecordingTime(time: Int) {
        _recordingTime.value = time
        _playTime.value = time / 1000
    }

    fun updateRecorderState() {
        _isPlayed.value = !isPlayed.value

        when (isPlayed.value) {
            true -> handleRecorderPlayed()
            false -> handleRecorderStopped()
        }
    }

    private fun handleRecorderPlayed() {
        startTimer()
        startProgressBar()
    }

    private fun startTimer() {
        if (recordingTime.value / 1000 == playTime.value) _playTime.value = 0

        recordingTimer = timer(period = 1000, initialDelay = 1000) {
            if (recordingTime.value / 1000 == playTime.value) {
                cancel()
                _playTime.value = recordingTime.value / 1000
                return@timer
            }
            _playTime.value++
        }
    }

    private fun startProgressBar() {
        progressTimer =
            timer(period = recordingTime.value.toLong() / HUNDRED_PERCENT) {
                if (progressRate.value > HUNDRED_PERCENT) {
                    _isPlayed.value = !isPlayed.value
                    _progressRate.value = 0
                    cancel()
                    return@timer
                }
                _progressRate.value++
            }
    }

    private fun handleRecorderStopped() {
        stopTimer()
        stopProgressBar()
    }

    private fun stopTimer() {
        recordingTimer?.cancel()
    }

    private fun stopProgressBar() {
        progressTimer?.cancel()
    }

    private fun findTagByGenreId(genre: List<Int>) {
        when (genre.contains(0)) {
            true -> _tags.value = listOf(Genre.getValue(-1))
            false -> {
                _tags.value = genre.map {
                    Genre.getValue(it)
                }
            }
        }
    }

    private enum class Emotion {
        JOY, SAD, SCARY, STRANGE, SHY, ALL;

        companion object {
            fun findBackgroundById(id: Int): Int {
                return when (Emotion.getValue(id)) {
                    JOY -> R.drawable.background_yellow
                    SAD -> R.drawable.background_blue
                    SCARY -> R.drawable.background_red
                    STRANGE -> R.drawable.background_purple
                    SHY -> R.drawable.background_pink
                    ALL -> R.drawable.background_white
                }
            }

            fun findIconById(id: Int): Int {
                return when (Emotion.getValue(id)) {
                    JOY -> R.drawable.feeling_l_joy
                    SAD -> R.drawable.feeling_l_sad
                    SCARY -> R.drawable.feeling_l_scary
                    STRANGE -> R.drawable.feeling_l_strange
                    SHY -> R.drawable.feeling_l_shy
                    ALL -> R.drawable.feeling_l_blank
                }
            }

            fun getValue(emotionId: Int): Emotion = values().find { emotion ->
                emotion.ordinal + 1 == emotionId
            } ?: ALL
        }
    }

    sealed interface ViewState {
        object Success : ViewState
        object Loading : ViewState
        object Idle : ViewState
    }

    companion object {
        private const val HUNDRED_PERCENT = 100
    }
}
