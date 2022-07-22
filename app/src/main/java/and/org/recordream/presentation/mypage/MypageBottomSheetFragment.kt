package and.org.recordream.presentation.mypage//package before.forget.feature.write

import and.org.recordream.R
import and.org.recordream.databinding.FragmentMypageBottomSheetBinding
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MypageBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMypageBottomSheetBinding
    private var amOrpm = ""
    private var hourvalue = 0
    private var minvalue = 0
    val hour = arrayOf<String>(
        "00",
        "01",
        "02",
        "03",
        "04",
        "05",
        "06",
        "07",
        "08",
        "09",
        "10",
        "11",
        "12"
    )

    private val viewModel by activityViewModels<MyPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_mypage_bottom_sheet,
            container,
            false
        )
        amOrpmSettiing()
        hourSettiing()
        minuteSettiing()
        clickSave()
        initDialog()

        return binding.root
    }

    private fun clickSave() {
        binding.tvMypageBottomseetStorage.setOnClickListener {
            this.dismiss()
        }
    }

    private fun amOrpmSettiing() {

        val str = arrayOf<String>("AM", "PM")
        binding.npDatepickerAmorpm.maxValue = 0
        binding.npDatepickerAmorpm.maxValue = (str.size - 1)
        binding.npDatepickerAmorpm.displayedValues = str
        binding.npDatepickerAmorpm.setOnValueChangedListener { numberPicker, i, i2 ->
            val i = numberPicker.value
            amOrpm = str[i]
            viewModel.setAmOrPm(amOrpm)
            binding.npDatepickerAmorpm.wrapSelectorWheel = false

        }
    }

    private fun hourSettiing() {

        binding.npDatepickerHour.minValue = 0
        binding.npDatepickerHour.maxValue = (hour.size - 1)
        binding.npDatepickerHour.displayedValues = hour

        binding.npDatepickerHour.setOnValueChangedListener { numberPicker, i, i2 ->
            val i = numberPicker.value
//            hourvalue = hour[i]
            hourvalue = i
            viewModel.setHour(hourvalue)
            binding.npDatepickerHour.wrapSelectorWheel = false
        }
    }

    private fun minuteSettiing() {
        binding.npDatepickerMinute.minValue = 0
        binding.npDatepickerMinute.maxValue = 59
        binding.npDatepickerMinute.setFormatter { String.format("%02d", it) }

        binding.npDatepickerMinute.wrapSelectorWheel = false

//        binding.npDatepickerMinute.displayedValues = minvalue

        binding.npDatepickerMinute.setOnValueChangedListener { numberPicker, i, i2 ->
            val i = numberPicker.value
            minvalue = i
            viewModel.setMinute(minvalue)
            binding.npDatepickerMinute.wrapSelectorWheel = false
        }
    }

    private fun initDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())

        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}

