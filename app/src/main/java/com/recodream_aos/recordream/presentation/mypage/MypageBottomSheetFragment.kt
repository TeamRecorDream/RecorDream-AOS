package com.recodream_aos.recordream.presentation.mypage // package before.forget.feature.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.FragmentMypageBottomSheetBinding

class MypageBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentMypageBottomSheetBinding? = null
    private val binding get() = _binding ?: error("binding이 초기화 되지 않았습니다.")
    private val viewModel by activityViewModels<MypageViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMypageBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickBtn()
        amOrpmSettiing()
        hourSettiing()
        minuteSettiing()
        initDialog()
    }

    private fun clickBtn() {
        binding.btnMypageSave.setOnClickListener {
            viewModel.setIsShow()
            viewModel.postPushAlam()
            viewModel.clickSaveTime(true)
            viewModel.getUser()
            this.dismiss()
        }
        binding.btnMypageCancle.setOnClickListener {
            viewModel.clickSaveTime(false)
            this.dismiss()
        }
    }

    private fun amOrpmSettiing() {
        val DayList = arrayOf("AM", "PM")
        binding.npMypageBottomDay.maxValue = 0
        binding.npMypageBottomDay.maxValue = (DayList.size - 1)
        binding.npMypageBottomDay.displayedValues = DayList
        if (viewModel.setDay == "AM") {
            binding.npMypageBottomDay.value = AM
        } else {
            binding.npMypageBottomDay.value = PM
        }
        binding.npMypageBottomDay.setOnValueChangedListener { numberPicker, _, _ ->
            val day = numberPicker.value
            viewModel.setDay = DayList[day]
            binding.npMypageBottomDay.wrapSelectorWheel = false
        }
    }

    private fun hourSettiing() {
        binding.npMypageBottomHour.minValue = 0
        binding.npMypageBottomHour.maxValue = 12
        binding.npMypageBottomHour.setFormatter {
            String.format(
                getString(R.string.myopage_format_date),
                it,
            )
        }
        binding.npMypageBottomHour.value = viewModel.setHour
        binding.npMypageBottomMinute.wrapSelectorWheel = false
        binding.npMypageBottomHour.setOnValueChangedListener { numberPicker, _, _ ->
            val hour = numberPicker.value
            viewModel.setHour = hour
            binding.npMypageBottomHour.wrapSelectorWheel = false
        }
    }

    private fun minuteSettiing() {
        binding.npMypageBottomMinute.minValue = 0
        binding.npMypageBottomMinute.maxValue = 59
        binding.npMypageBottomMinute.setFormatter {
            String.format(
                getString(R.string.myopage_format_date),
                it,
            )
        }
        binding.npMypageBottomMinute.wrapSelectorWheel = false
        binding.npMypageBottomMinute.value = viewModel.setMinute
        binding.npMypageBottomMinute.setOnValueChangedListener { numberPicker, _, _ ->
            val minute = numberPicker.value
            viewModel.setMinute = minute
            binding.npMypageBottomMinute.wrapSelectorWheel = false
        }
    }

    private fun initDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.behavior.saveFlags = BottomSheetBehavior.SAVE_FIT_TO_CONTENTS
    }

    companion object {
        const val AM = 0
        const val PM = 1
    }
}
