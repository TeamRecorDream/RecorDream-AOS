package and.org.recordream.util

import and.org.recordream.databinding.DetailDeleteDialogBinding
import and.org.recordream.databinding.MypageDropoutDialogBinding
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.annotation.LayoutRes

class CustomDialog(context: Context) {
    private lateinit var binding: MypageDropoutDialogBinding
    private lateinit var bindingDelete: DetailDeleteDialogBinding
    private val dialog = Dialog(context)

    private lateinit var inflater: LayoutInflater

    init {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(context)
    }

    fun showDeleteDialog(@LayoutRes layout: Int) {
        bindingDelete = DetailDeleteDialogBinding.inflate(inflater)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(bindingDelete.root)
            setCancelable(false)
        }
        bindingDelete.tvDetailCancel.setOnClickListener {
            dialog.dismiss()
        }

        bindingDelete.tvDetailDelete.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun showDropOutDialog(@LayoutRes layout: Int) {
        binding = MypageDropoutDialogBinding.inflate(inflater)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            setCancelable(false)
        }
        binding.btnMypgageCancle.setOnClickListener {
            dialog.dismiss()
        }

        binding.btnMypageOutaccount.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}