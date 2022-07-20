package and.org.recordream.presentation.write

import and.org.recordream.databinding.FragmentVoiceRecordBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class VoiceRecordFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentVoiceRecordBinding? = null
    private val binding get() = _binding ?: error("바인딩에 NULL 값이 들어갔어요!!")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVoiceRecordBinding.inflate(inflater, container, false)

        initDialog()
        saveClick()
        return binding.root
    }

    private fun initDialog() { // 바텀시트달기
        val bottomSheetDialog = BottomSheetDialog(requireContext())

        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun saveClick(){
        binding.tvVoiceSave.setOnClickListener {
            dismiss()
        }
    }
}
