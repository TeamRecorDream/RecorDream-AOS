package com.recodream_aos.recordream.presentation.record.recording // ktlint-disable package-name

import androidx.lifecycle.ViewModel
import com.recodream_aos.recordream.presentation.record.recording.uistate.PlayButtonState
import com.recodream_aos.recordream.presentation.record.recording.uistate.PlayButtonState.RECORDER_PLAY
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState.AFTER_RECORDING
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState.BEFORE_RECORDING
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState.ON_RECORDING
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Timer
import kotlin.concurrent.timer

class RecordBottomSheetViewModel : ViewModel() {
    private val _nowTime = MutableStateFlow(ZERO)
    val nowTime: StateFlow<Int> get() = _nowTime

    private val _replayTime = MutableStateFlow(ZERO)
    val replayTime: StateFlow<Int> get() = _replayTime

    private val _fullProgressBar = MutableStateFlow(false)
    val fullProgressBar: StateFlow<Boolean> get() = _fullProgressBar

    var recordingTime: Int = 0
        private set

    private var _playButtonState: MutableStateFlow<PlayButtonState> =
        MutableStateFlow(RECORDER_PLAY)
    val playButtonState: StateFlow<PlayButtonState> = _playButtonState

    private var _recordButtonState: MutableStateFlow<RecordButtonState> =
        MutableStateFlow(BEFORE_RECORDING)
    val recordButtonState: StateFlow<RecordButtonState> = _recordButtonState

    private var firstTimer: Timer? = null
    private var replayTimer: Timer? = null
    private var realTimer: Timer? = null

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
    }

    private fun stopRecording() {
        _recordButtonState.value = AFTER_RECORDING
        stopProgressBar()
        setFullProgressBar()
    }

    private fun resetRecording() {
        _recordButtonState.value = BEFORE_RECORDING
        stopProgressBar()
        clearProgressBar()
        clearReplayProgressBar()

        _playButtonState.value = RECORDER_PLAY
    }

    fun setFullProgressBarFalse() {
        _fullProgressBar.value = false
    }

    fun initProgressBar() {
        firstTimer = timer(period = ONE_PERCENT, initialDelay = ONE_PERCENT) {
            if (_nowTime.value > HUNDRED_PERCENT) cancel()
            ++_nowTime.value
        }
        initRealTimer()
    }

    fun stopProgressBar() {
        firstTimer?.cancel()
        replayTimer?.cancel()
        realTimer?.cancel()
    }

    fun clearProgressBar() {
        _nowTime.value = ZERO
        recordingTime = ZERO
    }

    fun setFullProgressBar() {
        _nowTime.value = HUNDRED_PERCENT
    }

    fun replayProgressBar() {
        replayTimer = timer(period = recordingTime.convertMilliseconds() / PERCENTAGE) {
            if (_replayTime.value > HUNDRED_PERCENT) {
                cancel()
                _fullProgressBar.value = true
            }
            ++_replayTime.value
        }
    }

    fun clearReplayProgressBar() {
        _replayTime.value = ZERO
    }

    private fun Int.convertMilliseconds(): Long = this * ONE_SECOND

    private fun initRealTimer() {
        realTimer = timer(period = ONE_SECOND, initialDelay = ONE_SECOND) {
            recordingTime++
        }
    }

    fun stopReplayProgressBar() {
        replayTimer?.cancel()
    }

    companion object {
        private const val ONE_PERCENT = 1800L
        private const val ZERO = 0
        private const val HUNDRED_PERCENT = 100
        private const val ONE_SECOND: Long = 1000
        private const val PERCENTAGE = 100
    }
}
