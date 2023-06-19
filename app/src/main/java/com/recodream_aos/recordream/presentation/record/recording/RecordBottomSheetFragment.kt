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
import com.recodream_aos.recordream.presentation.record.recording.uistate.PlayButtonState
import com.recodream_aos.recordream.presentation.record.recording.uistate.PlayButtonState.RECORDER_PLAY
import com.recodream_aos.recordream.presentation.record.recording.uistate.PlayButtonState.RECORDER_STOP
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState.AFTER_RECORDING
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState.BEFORE_RECORDING
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState.ON_RECORDING
import com.recodream_aos.recordream.util.Recorder.Recorder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecordBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentRecordBottomSheetBinding? = null
    val binding get() = _binding ?: error(R.string.error_basefragment)
    private val recordBottomSheetViewModel: RecordBottomSheetViewModel by viewModels()
    private val recordViewModel: RecordViewModel by activityViewModels()

    private val timeStampTextView: TimeStampTextView by lazy { binding.tvRecordingProgressTime }
    private val playButtonView: PlayButtonView by lazy { binding.ivRecordingPlayStateBtn }
    private val recorder: Recorder by lazy { Recorder(requireContext()) }
    private val playButtonState: PlayButtonState by lazy { initPlayButtonState() }
    private val activityResultLauncher: ActivityResultLauncher<String> by lazy { initActivityResultLauncher() }

    private fun initPlayButtonState(): PlayButtonState {
        playButtonView.updateIconWithState(playButtonState)

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
        collectRecordButtonState()
    }

    private fun initViewModel() {
        binding.viewModel = recordBottomSheetViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initRecordPermission() =
        activityResultLauncher.launch(Manifest.permission.RECORD_AUDIO)

    private fun setEventOnClickListener() {
        playButtonClickListener()
        closeButtonClickListener()
        saveButtonClickListener()
    }

    private fun collectRecordButtonState() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(lifecycle.currentState) {
                recordBottomSheetViewModel.recordButtonState.collectLatest { currentState ->
                    when (currentState) {
                        BEFORE_RECORDING -> handleResetRecording()
                        ON_RECORDING -> handleOnRecording()
                        AFTER_RECORDING -> handleAfterRecording()
                    }
                }
            }
        }
    }

    private fun handleResetRecording() {
        timeStampTextView.stopCountUp()
        timeStampTextView.clearCountTime()

        binding.tvRecordingRecordTime.text = "03:00"
        binding.tvRecordingProgressTime.visibility = View.VISIBLE
        binding.ivRecordingPlayStateBtn.visibility = View.GONE
        binding.ivRecordingCloseBtn.visibility = View.GONE
        binding.ivRecordingSaveBtn.visibility = View.GONE
    }

    private fun handleOnRecording() {
        recorder.startRecording()
        timeStampTextView.startCountUp()
        collectNowTimeProgress()
    }

    private fun handleAfterRecording() {
        recorder.stopRecording()
        timeStampTextView.stopCountUp()

        binding.tvRecordingRecordTime.text = timeStampTextView.text.toString() // 데이터바인딩

        binding.tvRecordingProgressTime.visibility = View.GONE
        binding.ivRecordingPlayStateBtn.visibility = View.VISIBLE
        binding.ivRecordingCloseBtn.visibility = View.VISIBLE
        binding.ivRecordingSaveBtn.visibility = View.VISIBLE
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
        //  playButtonState = RECORDER_STOP
        playButtonView.updateIconWithState(playButtonState)
        recordBottomSheetViewModel.clearReplayProgressBar()
        recorder.startPlaying()
        recordBottomSheetViewModel.replayProgressBar()
        collectReplayTimeProgress()
        timeStampTextView.startCountUp()
    }

    private fun stopPlayingRecorder() {
        // playButtonState = RECORDER_PLAY
        playButtonView.updateIconWithState(playButtonState)
        recorder.stopPlaying()
        recordBottomSheetViewModel.stopReplayProgressBar()
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
