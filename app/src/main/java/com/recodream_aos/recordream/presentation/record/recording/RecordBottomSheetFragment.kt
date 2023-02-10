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
import com.recodream_aos.recordream.util.recorder.RecordButton
import com.recodream_aos.recordream.util.recorder.RecordButtonState
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
    private val recordButton: RecordButton by lazy { binding.ivRecordingRecordBtn }
    private val playButton: PlayButton by lazy { binding.ivRecordingPlayBtn }
    private var recorder = Recorder
    private lateinit var activityResultLauncher: ActivityResultLauncher<String>
    private lateinit var recordButtonState: RecordButtonState
    private lateinit var playButtonState: PlayButtonState
    private var setRecordState = false

    // TODO : 코드정리

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBottomSheetBinding.inflate(layoutInflater)
        dialog?.setCanceledOnTouchOutside(false) // 한줄이면 외부막고 백버튼 가능..
        initViewModel()
        initRecorder()
        initActivityResultLauncher()
        recordButtonClickListener()
        playButtonClickListener()
        closeButtonClickListener()
        saveButtonClickListener()
        recordViewModel.getRecordState = false

        recordButtonState = RecordButtonState.BEFORE_RECORDING
        recordButton.updateIconWithState(recordButtonState)

        playButtonState = PlayButtonState.RECORDER_PLAY
        playButton.updateIconWithState(playButtonState)

        return binding.root
    }

    private fun initRecorder() = recorder.init(requireContext())

    private fun initViewModel() {
        binding.viewModel = recordBottomSheetViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
    }

    private fun initActivityResultLauncher() {
        val contract = ActivityResultContracts.RequestPermission()
        activityResultLauncher =
            registerForActivityResult(contract) { isGranted ->
                checkPermission(isGranted)
            }
        initRecordPermission()
    }

    private fun checkPermission(isGranted: Boolean) {
        if (!isGranted) {
            dismiss()
        }
    }

    private fun initRecordPermission() =
        activityResultLauncher.launch(Manifest.permission.RECORD_AUDIO)

    private fun recordButtonClickListener() {
        binding.ivRecordingRecordBtn.setOnClickListener {
            when (recordButtonState) {
                RecordButtonState.BEFORE_RECORDING -> startRecording()
                RecordButtonState.ON_RECORDING -> stopRecording()
                RecordButtonState.AFTER_RECORDING -> resetRecording()
            }
        }
    }

    private fun playButtonClickListener() {
        binding.ivRecordingPlayBtn.setOnClickListener {
            when (playButtonState) {
                PlayButtonState.RECORDER_PLAY -> startPlayingRecorder()
                PlayButtonState.RECORDER_STOP -> stopPlayingRecorder()
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
            dismiss()
        }
    }

    private fun startPlayingRecorder() {
        playButtonState = PlayButtonState.RECORDER_STOP
        playButton.updateIconWithState(playButtonState)
        recordBottomSheetViewModel.clearReplayProgressBar()
        recorder?.startPlaying()
        recordBottomSheetViewModel.replayProgressBar()
        collectReplayTimeProgress()
        timeStampTextView.startCountUp()
    }

    private fun stopPlayingRecorder() {
        playButtonState = PlayButtonState.RECORDER_PLAY
        playButton.updateIconWithState(playButtonState)
        recorder?.stopPlaying()
        recordBottomSheetViewModel.stopReplayProgressBar()
    }

    private fun startRecording() {
        recordButtonState = RecordButtonState.ON_RECORDING
        recordButton.updateIconWithState(recordButtonState)
        recorder.startRecording()
        timeStampTextView.startCountUp()
        recordBottomSheetViewModel.initProgressBar()
        collectNowTimeProgress()
    }

    private fun stopRecording() {
        recordButtonState = RecordButtonState.AFTER_RECORDING
        recordButton.updateIconWithState(recordButtonState)
        recorder?.stopRecording()
        timeStampTextView.stopCountUp()
        recordBottomSheetViewModel.stopProgressBar()
        recordBottomSheetViewModel.setFullProgressBar()
        binding.tvRecordingRecordTime.text = timeStampTextView.text.toString()

        binding.tvRecordingProgressTime.visibility = View.GONE
        binding.ivRecordingPlayBtn.visibility = View.VISIBLE
        binding.ivRecordingCloseBtn.visibility = View.VISIBLE
        binding.ivRecordingSaveBtn.visibility = View.VISIBLE
    }

    private fun resetRecording() {
        recorder?.stopPlaying()
        recordButtonState = RecordButtonState.BEFORE_RECORDING
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

        playButtonState = PlayButtonState.RECORDER_PLAY
        playButton.updateIconWithState(playButtonState)
        binding.tvRecordingRecordTime.text = "03:00"
        binding.tvRecordingProgressTime.visibility = View.VISIBLE
        binding.ivRecordingPlayBtn.visibility = View.GONE
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
        recorder?.recorderRelease()
        recorder?.playerRelease()
    }
}
