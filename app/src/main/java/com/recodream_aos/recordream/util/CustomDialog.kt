package com.recodream_aos.recordream.util
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.fragment.app.activityViewModels
import com.recodream_aos.recordream.databinding.CustomMypageDialogBinding
import com.recodream_aos.recordream.databinding.DocumentDeleteDialogBinding
import com.recodream_aos.recordream.presentation.mypage.MypageActivity
import com.recodream_aos.recordream.presentation.mypage.MypageViewModel
import dagger.hilt.android.AndroidEntryPoint


class CustomDialog(context: Context) : Dialog(context) {
    private lateinit var deleteBinding: DocumentDeleteDialogBinding
    private lateinit var mypageBinding: CustomMypageDialogBinding
    private val dialog = Dialog(context)
    private lateinit var inflater: LayoutInflater
    private lateinit var onClickedListener: ButtonClickListener


    fun interface ButtonClickListener {
        fun onClicked(num: Int)
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
            //onClickedListener.onClicked(1)
            dialog.dismiss()
            //(context as MypageActivity).finish()

        }
        dialog.show()
    }


//    fun showConfirmDialog(title: String) {
//        val binding = CustomMypageDialogBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        show()
//        window?.setLayout(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.WRAP_CONTENT
//        )
//        window?.setBackgroundDrawableResource(R.drawable.inset_horizontal_58)
//        binding.tvDialogCheck.setOnClickListener {
//            onClickedListener.onClicked(1)
//            dismiss()
//            (context as ThunderDetailActivity).finish()
//        }
////        findViewById<TextView>(R.id.tv_dialog_check).setOnClickListener {
////            onClickedListener.onClicked(1)
////            dismiss()
////            (context as ThunderDetailActivity).finish()
////        }
//    }

}
