package com.recodream_aos.recordream.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.annotation.LayoutRes
import com.recodream_aos.recordream.databinding.CustomMypageDialogBinding
import com.recodream_aos.recordream.databinding.DocumentDeleteDialogBinding


class CustomDialog(context: Context) {
    private lateinit var deleteBinding: DocumentDeleteDialogBinding
    private lateinit var mypageBinding: CustomMypageDialogBinding
    private val dialog = Dialog(context)
    private lateinit var inflater: LayoutInflater
    private lateinit var onClickedListener: ButtonClickListener

    fun interface ButtonClickListener {
        fun onClicked()
    }

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }

    init {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(context)
        }
    }

    fun showDeleteDialog(@LayoutRes layout: Int) {
        deleteBinding = DocumentDeleteDialogBinding.inflate(inflater)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(deleteBinding.root)
            setCancelable(false)
        }
        deleteBinding.tvDocumentCancel.setOnClickListener {
            dialog.dismiss()
        }
        deleteBinding.tvDocumentDelete.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun mypageShowDeleteDialog(@LayoutRes layout: Int) {
        mypageBinding = CustomMypageDialogBinding.inflate(inflater)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(mypageBinding.root)
//            setCancelable(false)
        }
        mypageBinding.btnMypageDialogCancle.setOnClickListener {
            dialog.dismiss()
        }
        mypageBinding.btnMypageDialogDelete.setOnClickListener {
            onClickedListener.onClicked()
            dialog.dismiss()
            //(context as MypageActivity).finish()

        }
        dialog.show()
    }


}
