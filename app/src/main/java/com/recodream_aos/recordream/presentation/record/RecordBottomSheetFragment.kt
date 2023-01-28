package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.FragmentRecordBottomSheetBinding

class RecordBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentRecordBottomSheetBinding? = null
    val binding get() = _binding ?: error(R.string.error_basefragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBottomSheetBinding.inflate(layoutInflater)
        return binding.root

        // 뷰모델 연결
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
