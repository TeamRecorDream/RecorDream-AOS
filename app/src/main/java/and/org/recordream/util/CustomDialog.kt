package and.org.recordream.util

import and.org.recordream.databinding.DetailDeleteDialogBinding
import and.org.recordream.databinding.MypageDropoutDialogBinding
import and.org.recordream.presentation.detail.DetailBottomSheetFragment
import and.org.recordream.presentation.mypage.MypageActivity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

class CustomDialog(private val context: MypageActivity) {
    private lateinit var binding: MypageDropoutDialogBinding
    private lateinit var bindingDelete: DetailDeleteDialogBinding
    private val dialog = Dialog(context)

    fun showDropOutDialog(@LayoutRes layout: Int) {
        binding = MypageDropoutDialogBinding.inflate(context.layoutInflater)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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

    fun showDeleteDialog(@LayoutRes layout: Int) {
        bindingDelete = DetailDeleteDialogBinding.inflate(context.layoutInflater)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
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


}