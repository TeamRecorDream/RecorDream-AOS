package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import android.Manifest
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.FragmentRecordBottomSheetBinding
import com.recodream_aos.recordream.util.recorder.RecordButton
import com.recodream_aos.recordream.util.recorder.RecordButtonState
import com.recodream_aos.recordream.util.recorder.TimeStampTextView
import java.util.Timer
import kotlin.concurrent.timer

class RecordBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentRecordBottomSheetBinding? = null
    val binding get() = _binding ?: error(R.string.error_basefragment)

    private val timeStampTextView: TimeStampTextView by lazy {
        binding.tvRecodingProgressTime
    }

    private val recordButton: RecordButton by lazy {
        binding.ivRecodingBtn
    }
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private val contract = ActivityResultContracts.RequestPermission()
    private val activityResultLauncher = registerForActivityResult(contract) { isGranted ->
        if (!isGranted) {
            // 권한이 필요한 작업 수행
            // 권한 꼭 있어야함미다 라는 문구 출력 ㄱㄱ
        }
    }
    private var buttonState = RecordButtonState.BEFORE_RECORDING
        set(value) {
            field = value
            binding.ivRecodingBtn.isEnabled =
                ((value == RecordButtonState.ON_RECORDING) or (value == RecordButtonState.AFTER_RECORDING))
            recordButton.updateIconWithState(value)
        }
    private val recordingFilePath by lazy {
        "${activity?.externalCacheDir?.absolutePath}/audiorecordtest.3gp"
    }

    private var timer: Timer? = null
    var nowTime = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBottomSheetBinding.inflate(layoutInflater)

        activityResultLauncher.launch(Manifest.permission.RECORD_AUDIO)

        binding.ivRecodingBtn.setOnClickListener {
            Log.d("아아", "아아")
            startRecording()
        }
        return binding.root
    }

    override fun onStop() {
        recorderRelease()
        playerRelease()
        super.onStop()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
//
//    private fun onRecord(start: Boolean) = if (start) {
//        startRecording()
//    } else {
//        stopRecording()
//    }
//
//    private fun onPlay(start: Boolean) = if (start) {
//        startPlaying()
//    } else {
//        stopPlaying()
//    }
//
//    private fun startPlaying() {
//        player = MediaPlayer().apply {
//            try {
//                setDataSource(fileName)
//                prepare()
//                start()
//            } catch (e: IOException) {
//                Log.e(LOG_TAG, "prepare() failed")
//            }
//        }
//    }
//
//    private fun stopPlaying() {
//        player?.release()
//        player = null
//    }

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC) // 사용마이크
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) // 포멧
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // 인코딩
            setOutputFile(recordingFilePath) // 캐시에 저장
            prepare()
        }
        recorder?.start()
        timeStampTextView.startCountUp()
        initTimer()
        buttonState = RecordButtonState.ON_RECORDING
    }

    private fun stopRecording() {
        recorder?.run {
            stop()
            release()
        }
        recorder = null
        timeStampTextView.stopCountUp()
        buttonState = RecordButtonState.AFTER_RECORDING
    }

    private fun recorderRelease() {
        recorder?.release()
        recorder = null
    }

    private fun playerRelease() {
        player?.release()
        player = null
    }

    private fun initTimer() {
        timer = timer(period = 1800, initialDelay = 1000) {
            if (nowTime > 100) cancel()
            binding.pbRecordingProgressBar.progress = ++nowTime
        }
    }

    companion object {
        private const val ONE_SECOND = 10L
        private const val THREE_MINUTE = 300
        private const val REQUEST_RECORD_AUDIO_PERMISSIONe = 200
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
}
