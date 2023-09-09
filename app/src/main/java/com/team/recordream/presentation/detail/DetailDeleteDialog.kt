package com.team.recordream.presentation.detail

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.team.recordream.databinding.DocumentDeleteDialogBinding

class DetailDeleteDialog(context: Context) {
    private val binding: DocumentDeleteDialogBinding by lazy {
        DocumentDeleteDialogBinding.inflate(LayoutInflater.from(context))
    }
    private val dialog by lazy { Dialog(context) }

    init {
        dialog.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(binding.root)
        }
    }

    fun create(detailViewModel: DetailViewModel) {
        dialog.show()
        setEventOnClick(detailViewModel)
    }

    private fun setEventOnClick(detailViewModel: DetailViewModel) {
        binding.tvDocumentCancel.setOnClickListener {
            dialog.dismiss()
        }

        binding.tvDocumentDelete.setOnClickListener {
            detailViewModel.updateRecordDeleted()
            dialog.dismiss()
        }
    }
}
