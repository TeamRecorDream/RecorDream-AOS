package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import android.media.MediaRecorder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.FragmentRecordBottomSheetBinding

class RecordBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentRecordBottomSheetBinding? = null
    val binding get() = _binding ?: error(R.string.error_basefragment)
    private lateinit var recorder
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun temp(){
        // MediaRecorder 생성
        recorder = MediaRecorder().apply {
            // 오디오 소스 설정
            setAudioSource(MediaRecorder.AudioSource.MIC)
            // 출력 파일 포맷 설정
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            // 오디오 인코더를 설정
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            // 출력 파일 이름 설정
            setOutputFile(recordingFilePath)
            // 초기화 완료
            prepare()
        }
        recorder?.start() // 녹음 시작
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}
