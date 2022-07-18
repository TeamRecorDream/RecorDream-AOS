package and.org.recordream.presentation.detail

import and.org.recordream.R
import and.org.recordream.databinding.FragmentDetailBottomSheetBinding
import and.org.recordream.util.CustomDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DetailBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDetailBottomSheetBinding
    private lateinit var dialog: CustomDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail_bottom_sheet,
            container,
            false
        )
        initDialog()
        clickEvent()
        return binding.root
    }

    private fun initDialog() { // 바텀시트달기
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun clickEvent() {
        binding.tvDetailBottomDelete.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        binding.tvDetailBottomDelete.setOnClickListener {
            dialog = CustomDialog(this)
            dialog.showDeleteDialog(R.layout.detail_delete_dialog)
        }

    }
}