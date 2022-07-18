package and.org.recordream.presentation.mypage//package before.forget.feature.write

import and.org.recordream.R
import and.org.recordream.databinding.FragmentMypageBottomSheetBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MypageBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMypageBottomSheetBinding

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
        initDialog()
        return binding.root
    }

    private fun amOrpmSettiing() { // 바텀시트 오류발생부분
        var amOrpm = ""
        var min = 0
        val str = arrayOf<String>("AM", "PM")
        binding.npDatepickerAmorpm.maxValue = 0
        binding.npDatepickerAmorpm.maxValue = (str.size - 1)
        binding.npDatepickerAmorpm.displayedValues = str

        binding.npDatepickerAmorpm.setOnValueChangedListener { numberPicker, i, i2 ->
            val i = numberPicker.value
            amOrpm = str[i]
        }
    }

    private fun initDialog() { // 바텀시트달기
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}

