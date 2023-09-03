package com.team.recordream.presentation.record.recording // ktlint-disable package-name

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.team.recordream.R
import com.team.recordream.databinding.FragmentRecordBottomSheetBinding
import com.team.recordream.presentation.common.model.PlayButtonState.RECORDER_PLAY
import com.team.recordream.presentation.common.model.PlayButtonState.RECORDER_STOP
import com.team.recordream.presentation.record.RecordViewModel
import com.team.recordream.presentation.record.recording.RecordBottomSheetViewModel.SavingRecordingState.DISCONNECT
import com.team.recordream.presentation.record.recording.RecordBottomSheetViewModel.SavingRecordingState.IDLE
import com.team.recordream.presentation.record.recording.RecordBottomSheetViewModel.SavingRecordingState.INVALID
import com.team.recordream.presentation.record.recording.RecordBottomSheetViewModel.SavingRecordingState.VALID
import com.team.recordream.presentation.record.recording.uistate.RecordButtonState.AFTER_RECORDING
import com.team.recordream.presentation.record.recording.uistate.RecordButtonState.BEFORE_RECORDING
import com.team.recordream.presentation.record.recording.uistate.RecordButtonState.ON_RECORDING
import com.team.recordream.util.recorder.Recorder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
        launchAudioPermission()
        initViewModel()
        observeStateFlows()
        setEventOnClickListener()
    }

    private fun launchAudioPermission() {
        activityResultLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    private fun initViewModel() {
        binding.viewModel = recordBottomSheetViewModel
        binding.lifecycleOwner = viewLifecycleOwner
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

    private fun handleResetRecording() {
        recordBottomSheetViewModel.stopCountUp()
        recordBottomSheetViewModel.clearCountTime()
        binding.tvRecordingRecordTime.text = getString(R.string.default_time_format)
    }

    private fun handleOnRecording() {
        recorder.startRecording()
        recordBottomSheetViewModel.startCountUp()
    }

    private fun handleAfterRecording() {
        recorder.stopRecording()
        recordBottomSheetViewModel.apply {
            stopCountUp()
            binding.tvRecordingRecordTime.text = countTime.value
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

    private fun handleRecorderPlayingState() {
        recorder.stopPlaying()
    }

    private fun handleRecorderStoppingState() {
        recorder.startPlaying()
        recordBottomSheetViewModel.startCountUp()
    }

    private fun collectStateProgressBar() {
        collectWithLifecycle(recordBottomSheetViewModel.fullProgressBar) { state ->
            if (state) recorder.stopPlaying()
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

    private fun closeButtonClickListener() {
        binding.ivRecordingCloseBtn.setOnClickListener { dismiss() }
    }

    private fun saveButtonClickListener() {
        binding.ivRecordingSaveBtn.setOnClickListener {
            initNetwork()
        }
    }

    private fun initNetwork() {
        recordBottomSheetViewModel.postVoice(recorder.getRecordingFile())

        collectWithLifecycle(recordBottomSheetViewModel.stateOfSavingRecording) { result ->
            when (result) {
                is VALID -> recordViewModel.updateId(result.voiceRecordId.id)
                is INVALID -> Log.e("RecordBottomSheetFragment", "에러 핸들링 필요")
                is DISCONNECT -> Log.e("RecordBottomSheetFragment", "에러 핸들링 필요")
                is IDLE -> Log.e("RecordBottomSheetFragment", "DEFAULT")
            }

            if (result !is IDLE) setEventOnCloseDialog()
        }
    }

    private fun setEventOnCloseDialog() {
        recordViewModel.updateRecordingTime(binding.tvRecordingRecordTime.text.toString())
        dismiss()
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
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest { value ->
                    action(value)
                }
            }
        }
    }
}
