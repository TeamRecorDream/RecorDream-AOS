package com.recodream_aos.recordream.presentation.document

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.FragmentDocumentBottomSheetBinding
import com.recodream_aos.recordream.util.CustomDialog

class DocumentBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDocumentBottomSheetBinding
    private lateinit var dialogDelete: CustomDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_document_bottom_sheet,
            container,
            false
        )
        initDialog()
        clickShare()
        clickEvent()
        clickCancel()
        return binding.root
    }

    private fun initDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun clickShare() {
        binding.tvDocumentBottomShare.setOnClickListener {
        }
    }

    private fun clickEvent() {
        binding.tvDocumentBottomDelete.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        binding.tvDocumentBottomDelete.setOnClickListener {
            dialogDelete = CustomDialog(requireActivity())
            dialogDelete.showDeleteDialog(R.layout.document_delete_dialog)
        }
    }

    private fun clickCancel() {
        binding.btnDocumentBottomCancel.setOnClickListener {
            dismiss()
        }
    }
}
