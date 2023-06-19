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
import com.recodream_aos.recordream.presentation.record.recording.uistate.PlayButtonState.RECORDER_PLAY
import com.recodream_aos.recordream.presentation.record.recording.uistate.PlayButtonState.RECORDER_STOP
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState.AFTER_RECORDING
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState.BEFORE_RECORDING
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState.ON_RECORDING
import com.recodream_aos.recordream.util.Recorder.Recorder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecordBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentRecordBottomSheetBinding? = null
    val binding get() = _binding ?: error(R.string.error_basefragment)
    private val recordBottomSheetViewModel: RecordBottomSheetViewModel by viewModels()
    private val recordViewModel: RecordViewModel by activityViewModels()
    private val recorder: Recorder by lazy { Recorder(requireContext()) }
    private val activityResultLauncher: ActivityResultLauncher<String> by lazy {
        val contract = ActivityResultContracts.RequestPermission()
        registerForActivityResult(contract) { isGranted -> if (!isGranted) dismiss() }
    }
    private val timeStampTextView: TimeStampTextView by lazy { binding.tvRecordingProgressTime }

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
        launchAudioPermission()
        observeStateFlows()
        setEventOnClickListener()
    }

    private fun initViewModel() {
        binding.viewModel = recordBottomSheetViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun launchAudioPermission() {
        activityResultLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    private fun observeStateFlows() {
        collectRecordButtonState()
        collectPlayButtonState()
        collectStateProgressBar()
        collectReplayTimeProgress()
        collectNowTimeProgress()
    }

    private fun collectRecordButtonState() {
        collectWithLifecycle(recordBottomSheetViewModel.recordButtonState) { currentState ->
            when (currentState) {
                BEFORE_RECORDING -> handleResetRecording()
                ON_RECORDING -> handleOnRecording()
                AFTER_RECORDING -> handleAfterRecording()
            }
        }
    }

    private fun collectPlayButtonState() {
        collectWithLifecycle(recordBottomSheetViewModel.playButtonState) { currentState ->
            when (currentState) {
                RECORDER_PLAY -> handleRecorderPlayingState()
                RECORDER_STOP -> handleRecorderStoppingState()
            }
        }
    }

    private fun collectStateProgressBar() {
        collectWithLifecycle(recordBottomSheetViewModel.fullProgressBar) { state ->
            if (state) {
                recordBottomSheetViewModel.updatePlayButtonState(RECORDER_STOP)
                recorder.stopPlaying()
                recordBottomSheetViewModel.stopReplayProgressBar()
                recordBottomSheetViewModel.setFullProgressBarFalse()
            }
        }
    }

    private fun collectReplayTimeProgress() {
        collectWithLifecycle(recordBottomSheetViewModel.replayTime) { progress ->
            binding.pbRecordingProgressBar.progress = progress
        }
    }

    private fun collectNowTimeProgress() {
        collectWithLifecycle(recordBottomSheetViewModel.nowTime) { progress ->
            binding.pbRecordingProgressBar.progress = progress
        }
    }

    private fun setEventOnClickListener() {
        closeButtonClickListener()
        saveButtonClickListener()
    }

    private fun handleRecorderStoppingState() {
        recorder.startPlaying()
        timeStampTextView.startCountUp()
    }

    private fun handleRecorderPlayingState() {
        recorder.stopPlaying()
    }

    private fun handleResetRecording() {
        timeStampTextView.stopCountUp()
        timeStampTextView.clearCountTime()

        binding.tvRecordingRecordTime.text = "03:00"
    }

    private fun handleOnRecording() {
        recorder.startRecording()
        timeStampTextView.startCountUp()
    }

    private fun handleAfterRecording() {
        recorder.stopRecording()
        timeStampTextView.stopCountUp()

        binding.tvRecordingRecordTime.text = timeStampTextView.text.toString() // 데이터바인딩
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onStop() {
        super.onStop()
        recorder.recorderRelease()
        recorder.playerRelease()
    }

    private inline fun <T> collectWithLifecycle(
        flow: Flow<T>,
        crossinline action: (T) -> Unit,
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(lifecycle.currentState) {
                flow.collectLatest { value ->
                    action(value)
                }
            }
        }
    }
}
