package com.recodream_aos.recordream.presentation.record.recording // ktlint-disable package-name

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.FragmentRecordBottomSheetBinding
import com.recodream_aos.recordream.presentation.record.RecordViewModel
import com.recodream_aos.recordream.util.recorder.PlayButton
import com.recodream_aos.recordream.util.recorder.PlayButtonState
import com.recodream_aos.recordream.util.recorder.PlayButtonState.RECORDER_PLAY
import com.recodream_aos.recordream.util.recorder.PlayButtonState.RECORDER_STOP
import com.recodream_aos.recordream.util.recorder.RecordButton
import com.recodream_aos.recordream.util.recorder.RecordButtonState
import com.recodream_aos.recordream.util.recorder.RecordButtonState.AFTER_RECORDING
import com.recodream_aos.recordream.util.recorder.RecordButtonState.BEFORE_RECORDING
import com.recodream_aos.recordream.util.recorder.RecordButtonState.ON_RECORDING
import com.recodream_aos.recordream.util.recorder.Recorder
import com.recodream_aos.recordream.util.recorder.TimeStampTextView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecordBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentRecordBottomSheetBinding? = null
    val binding get() = _binding ?: error(R.string.error_basefragment)
    private val recordBottomSheetViewModel: RecordBottomSheetViewModel by viewModels()
    private val recordViewModel: RecordViewModel by activityViewModels()

    private val timeStampTextView: TimeStampTextView by lazy { binding.tvRecordingProgressTime }
    private val recordButton: RecordButton by lazy { binding.ivRecordingRecordStateBtn }
    private val playButton: PlayButton by lazy { binding.ivRecordingPlayStateBtn }
    private val recorder: Recorder by lazy { Recorder(requireContext()) }
    private val recordButtonState: RecordButtonState by lazy { initRecordButtonState() }
    private val playButtonState: PlayButtonState by lazy { initPlayButtonState() }
    private val activityResultLauncher: ActivityResultLauncher<String> by lazy { initActivityResultLauncher() }

    private fun initRecordButtonState(): RecordButtonState {
        recordButton.updateIconWithState(recordButtonState)
        return BEFORE_RECORDING
    }

    private fun initPlayButtonState(): PlayButtonState {
        playButton.updateIconWithState(playButtonState)

        return RECORDER_PLAY
    }

    private fun initActivityResultLauncher(): ActivityResultLauncher<String> {
        val contract = ActivityResultContracts.RequestPermission()

        return registerForActivityResult(contract) { isGranted -> if (!isGranted) dismiss() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRecordBottomSheetBinding.inflate(layoutInflater)
        dialog?.setCanceledOnTouchOutside(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecordPermission()
        setEventOnClickListener()
    }

    private fun initViewModel() {
        binding.viewModel = recordBottomSheetViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initRecordPermission() =
        activityResultLauncher.launch(Manifest.permission.RECORD_AUDIO)

    private fun setEventOnClickListener() {
        recordButtonClickListener()
        playButtonClickListener()
        closeButtonClickListener()
        saveButtonClickListener()
    }

    private fun recordButtonClickListener() {
        binding.ivRecordingRecordStateBtn.setOnClickListener {
            when (recordButtonState) {
                BEFORE_RECORDING -> startRecording()
                ON_RECORDING -> stopRecording()
                AFTER_RECORDING -> resetRecording()
            }
        }
    }

    private fun playButtonClickListener() {
        binding.ivRecordingPlayStateBtn.setOnClickListener {
            when (playButtonState) {
                RECORDER_PLAY -> startPlayingRecorder()
                RECORDER_STOP -> stopPlayingRecorder()
            }
            collectStateProgressBar()
        }
    }

    private fun closeButtonClickListener() {
        binding.ivRecordingCloseBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun saveButtonClickListener() {
        binding.ivRecordingSaveBtn.setOnClickListener {
            recordViewModel.getRecordState = true
            // recordViewModel.getRecordingTime(recordBottomSheetViewModel.recordingTime)
            dismiss()
        }
    }

    private fun startPlayingRecorder() {
        playButtonState = RECORDER_STOP
        playButton.updateIconWithState(playButtonState)
        recordBottomSheetViewModel.clearReplayProgressBar()
        recorder.startPlaying()
        recordBottomSheetViewModel.replayProgressBar()
        collectReplayTimeProgress()
        timeStampTextView.startCountUp()
    }

    private fun stopPlayingRecorder() {
        playButtonState = RECORDER_PLAY
        playButton.updateIconWithState(playButtonState)
        recorder.stopPlaying()
        recordBottomSheetViewModel.stopReplayProgressBar()
    }

    private fun startRecording() {
        recordButtonState = ON_RECORDING
        recordButton.updateIconWithState(recordButtonState)
        recorder.startRecording()
        timeStampTextView.startCountUp()
        recordBottomSheetViewModel.initProgressBar()
        collectNowTimeProgress()
    }

    private fun stopRecording() {
        recordButtonState = AFTER_RECORDING
        recordButton.updateIconWithState(recordButtonState)
        recorder.stopRecording()
        timeStampTextView.stopCountUp()
        recordBottomSheetViewModel.stopProgressBar()
        recordBottomSheetViewModel.setFullProgressBar()
        binding.tvRecordingRecordTime.text = timeStampTextView.text.toString()

        binding.tvRecordingProgressTime.visibility = View.GONE
        binding.ivRecordingPlayStateBtn.visibility = View.VISIBLE
        binding.ivRecordingCloseBtn.visibility = View.VISIBLE
        binding.ivRecordingSaveBtn.visibility = View.VISIBLE
    }

    private fun resetRecording() {
        recorder.stopPlaying()
        recordButtonState = BEFORE_RECORDING
        recordButton.updateIconWithState(recordButtonState)
        timeStampTextView.stopCountUp()
        timeStampTextView.clearCountTime()
        recordBottomSheetViewModel.stopProgressBar()
        recordBottomSheetViewModel.clearProgressBar()
        recordBottomSheetViewModel.clearReplayProgressBar()

        // 음성녹음 확인해보기
        // 게이지 차오르는 주기가 조금 느림 period 확인
        // 저장버튼 누를 시, 음성 파일 recordViewModel에서 갖고있기.
        // 코드정리

        playButtonState = RECORDER_PLAY
        playButton.updateIconWithState(playButtonState)
        binding.tvRecordingRecordTime.text = "03:00"
        binding.tvRecordingProgressTime.visibility = View.VISIBLE
        binding.ivRecordingPlayStateBtn.visibility = View.GONE
        binding.ivRecordingCloseBtn.visibility = View.GONE
        binding.ivRecordingSaveBtn.visibility = View.GONE
    }

    private fun collectStateProgressBar() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(lifecycle.currentState) {
                recordBottomSheetViewModel.fullProgressBar.collectLatest { state ->
                    if (state) {
                        stopPlayingRecorder()
                        recordBottomSheetViewModel.setFullProgressBarFalse()
                    }
                }
            }
        }
    }

    private fun collectReplayTimeProgress() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(lifecycle.currentState) {
                recordBottomSheetViewModel.replayTime.collectLatest { progress ->
                    binding.pbRecordingProgressBar.progress = progress
                }
            }
        }
    }

    private fun collectNowTimeProgress() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(lifecycle.currentState) {
                recordBottomSheetViewModel.nowTime.collectLatest { progress ->
                    binding.pbRecordingProgressBar.progress = progress
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onStop() {
        super.onStop()
        recorder.recorderRelease()
        recorder.playerRelease()
    }
}
