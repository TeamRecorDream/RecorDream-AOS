package com.recodream_aos.recordream.presentation.record.recording // ktlint-disable package-name

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.util.CustomResult.FAIL
import com.example.domain.util.CustomResult.SUCCESS
import com.recodream_aos.recordream.domain.model.VoiceRecord
import com.recodream_aos.recordream.domain.repository.RecordRepository
import com.recodream_aos.recordream.presentation.record.recording.RecordBottomSheetViewModel.SavingState.DISCONNECT
import com.recodream_aos.recordream.presentation.record.recording.RecordBottomSheetViewModel.SavingState.IDLE
import com.recodream_aos.recordream.presentation.record.recording.RecordBottomSheetViewModel.SavingState.INVALID
import com.recodream_aos.recordream.presentation.record.recording.RecordBottomSheetViewModel.SavingState.VALID
import com.recodream_aos.recordream.presentation.record.recording.uistate.PlayButtonState
import com.recodream_aos.recordream.presentation.record.recording.uistate.PlayButtonState.RECORDER_PLAY
import com.recodream_aos.recordream.presentation.record.recording.uistate.PlayButtonState.RECORDER_STOP
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState.AFTER_RECORDING
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState.BEFORE_RECORDING
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState.ON_RECORDING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.timer

@HiltViewModel
class RecordBottomSheetViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
) : ViewModel() {
    private var firstTimer: Timer? = null
    private var replayTimer: Timer? = null
    private var realTimer: Timer? = null
    private var countUpJob: Job? = null
    private var recordingTime: Int = INIT_TO_ZERO
    private var startTimeStamp: Long = INIT_TO_ZERO_LONG

    private val _countTime: MutableStateFlow<String> = MutableStateFlow(DEFAULT_MAX_TIME)
    val countTime: StateFlow<String> = _countTime

    private val _nowTime = MutableStateFlow(INIT_TO_ZERO)
    val nowTime: StateFlow<Int> get() = _nowTime

    private val _replayTime = MutableStateFlow(INIT_TO_ZERO)
    val replayTime: StateFlow<Int> get() = _replayTime

    private val _fullProgressBar = MutableStateFlow(false)
    val fullProgressBar: StateFlow<Boolean> get() = _fullProgressBar

    private val _isRecorderActivated = MutableStateFlow(false)
    val isRecorderActivated: StateFlow<Boolean> get() = _isRecorderActivated

    private val _playButtonState: MutableStateFlow<PlayButtonState> =
        MutableStateFlow(RECORDER_PLAY)
    val playButtonState: StateFlow<PlayButtonState> = _playButtonState

    private val _recordButtonState: MutableStateFlow<RecordButtonState> =
        MutableStateFlow(BEFORE_RECORDING)
    val recordButtonState: StateFlow<RecordButtonState> = _recordButtonState

    private val _stateOfSavingRecording: MutableStateFlow<SavingState> =
        MutableStateFlow(IDLE)
    val stateOfSavingRecording: StateFlow<SavingState> = _stateOfSavingRecording

    fun postVoice(recordingFile: File) {
        viewModelScope.launch {
            runCatching { recordRepository.postVoice(recordingFile) }
                .onSuccess { result ->
                    when (result) {
                        is SUCCESS -> _stateOfSavingRecording.value = VALID(result.data)
                        is FAIL -> _stateOfSavingRecording.value = INVALID
                    }
                }
                .onFailure { _stateOfSavingRecording.value = DISCONNECT }
        }
    }

    fun updatePlayButtonState(beforeState: PlayButtonState) {
        when (beforeState) {
            RECORDER_PLAY -> startPlayingRecorder()
            RECORDER_STOP -> stopPlayingRecorder()
        }
    }

    private fun startPlayingRecorder() {
        _playButtonState.value = RECORDER_STOP
        _replayTime.value = INIT_TO_ZERO
        replayProgressBar()
    }

    private fun replayProgressBar() {
        replayTimer = timer(period = recordingTime.convertMilliseconds() / PERCENTAGE) {
            if (_replayTime.value > HUNDRED_PERCENT) {
                cancel()
                stopReplayProgressBar()
                _fullProgressBar.value = true
            }
            ++_replayTime.value
        }
    }

    private fun stopPlayingRecorder() {
        _playButtonState.value = RECORDER_PLAY
        stopReplayProgressBar()
    }

    private fun stopReplayProgressBar() {
        replayTimer?.cancel()
    }

    fun updateRecordButtonState(beforeState: RecordButtonState) {
        when (beforeState) {
            BEFORE_RECORDING -> startRecording()
            ON_RECORDING -> stopRecording()
            AFTER_RECORDING -> resetRecording()
        }
    }

    private fun startRecording() {
        _recordButtonState.value = ON_RECORDING
        initProgressBar()
        initRealTimer()
    }

    private fun initProgressBar() {
        firstTimer = timer(period = ONE_PERCENT, initialDelay = ONE_PERCENT) {
            if (_nowTime.value > HUNDRED_PERCENT) cancel()
            ++_nowTime.value
        }
    }

    private fun initRealTimer() {
        realTimer = timer(period = ONE_SECOND, initialDelay = ONE_SECOND) {
            recordingTime++
        }
    }

    private fun stopRecording() {
        _recordButtonState.value = AFTER_RECORDING
        _isRecorderActivated.value = true
        stopProgressBar()
        _nowTime.value = HUNDRED_PERCENT
    }

    private fun resetRecording() {
        _recordButtonState.value = BEFORE_RECORDING
        _isRecorderActivated.value = false
        stopProgressBar()
        clearProgressBar()

        _playButtonState.value = RECORDER_PLAY
    }

    private fun stopProgressBar() {
        firstTimer?.cancel()
        replayTimer?.cancel()
        realTimer?.cancel()
    }

    private fun clearProgressBar() {
        _nowTime.value = INIT_TO_ZERO
        recordingTime = INIT_TO_ZERO
        _replayTime.value = INIT_TO_ZERO
    }

    fun startCountUp() {
        startTimeStamp = SystemClock.elapsedRealtime()
        countUpJob = viewModelScope.launch {
            while (isActive) {
                val currentTimeStamp = SystemClock.elapsedRealtime()
                val countTimeSeconds = ((currentTimeStamp - startTimeStamp) / ONE_SECOND).toInt()
                updateCountTime(countTimeSeconds)
                delay(ONE_SECOND)
            }
        }
    }

    fun stopCountUp() {
        countUpJob?.cancel()
    }

    fun clearCountTime() {
        updateCountTime(INIT_TO_ZERO)
    }

    private fun updateCountTime(countTimeSeconds: Int) {
        val minutes = countTimeSeconds / ONE_MINUTE
        val seconds = countTimeSeconds % ONE_MINUTE
        val timeText = TIME_FORMAT.format(minutes, seconds)
        _countTime.value = timeText
    }

    private fun Int.convertMilliseconds(): Long = this * ONE_SECOND

    sealed interface SavingState {
        data class VALID(val voiceRecord: VoiceRecord) : SavingState
        object INVALID : SavingState
        object DISCONNECT : SavingState
        object IDLE : SavingState
    }

    companion object {
        private const val ONE_PERCENT = 1800L
        private const val INIT_TO_ZERO_LONG: Long = 0
        private const val INIT_TO_ZERO: Int = 0
        private const val HUNDRED_PERCENT = 100
        private const val ONE_SECOND: Long = 1000
        private const val PERCENTAGE = 100
        private const val ONE_MINUTE = 60
        private const val TIME_FORMAT = "%02d:%02d"
        private const val DEFAULT_MAX_TIME = "03:00"
    }
}
