package and.org.recordream.util

import and.org.recordream.databinding.MypageDropoutDialogBinding
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

class CustomDialog(private val context: AppCompatActivity) {
    private lateinit var binding: MypageDropoutDialogBinding
    private val dialog = Dialog(context)

    fun showDeleteDialog(@LayoutRes layout: Int) {
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


}