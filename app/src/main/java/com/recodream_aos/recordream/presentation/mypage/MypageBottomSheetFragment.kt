package com.recodream_aos.recordream.presentation.mypage//package before.forget.feature.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.recodream_aos.recordream.databinding.FragmentMypageBottomSheetBinding

class MypageBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentMypageBottomSheetBinding? = null
    private val binding get() = _binding ?: error("binding이 초기화 되지 않았습니다.")
    private var amOrpm = ""
    private var hourvalue = 0
    private var minvalue = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBottomSheetBinding.inflate(inflater, container, false)
        amOrpmSettiing()
        hourSettiing()
        minuteSettiing()
        clickBtn()
        initDialog()

        return binding.root
    }

    private fun clickBtn() {
        binding.btnMypageSave.setOnClickListener {
            this.dismiss()
        }
        binding.btnMypageCancle.setOnClickListener {
            this.dismiss()
        }
    }

    private fun amOrpmSettiing() {

        val str = arrayOf<String>("AM", "PM")
        binding.npMypageBottomDay.maxValue = 0
        binding.npMypageBottomDay.maxValue = (str.size - 1)
        binding.npMypageBottomDay.displayedValues = str
        binding.npMypageBottomDay.setOnValueChangedListener { numberPicker, i, i2 ->
            val i = numberPicker.value
            amOrpm = str[i]
//            viewModel.setAmOrPm(amOrpm)
            binding.npMypageBottomDay.wrapSelectorWheel = false

        }
    }

    private fun hourSettiing() {

        binding.npMypageBottomHour.minValue = 0
        binding.npMypageBottomHour.maxValue = 12
        binding.npMypageBottomHour.setFormatter { String.format("%02d", it) }
        binding.npMypageBottomHour.setOnValueChangedListener { numberPicker, i, i2 ->
            val i = numberPicker.value
            hourvalue = i
//            viewModel.setHour(hourvalue)
            binding.npMypageBottomHour.wrapSelectorWheel = false
        }
    }

    private fun minuteSettiing() {
        binding.npMypageBottomMinute.minValue = 0
        binding.npMypageBottomMinute.maxValue = 59
        binding.npMypageBottomMinute.setFormatter { String.format("%02d", it) }

        binding.npMypageBottomMinute.wrapSelectorWheel = false

        binding.npMypageBottomMinute.setOnValueChangedListener { numberPicker, i, i2 ->
            val i = numberPicker.value
            minvalue = i
//            viewModel.setMinute(minvalue)
            binding.npMypageBottomMinute.wrapSelectorWheel = false
        }
    }

    private fun initDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())

        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}

