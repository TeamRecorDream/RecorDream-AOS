package and.org.recordream.util

import and.org.recordream.databinding.DetailDeleteDialogBinding
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

class CustomDialog(private val context: AppCompatActivity) {
    private lateinit var binding: DetailDeleteDialogBinding
    private val dialog = Dialog(context)

    fun showDeleteDialog(@LayoutRes layout: Int) {
        binding = DetailDeleteDialogBinding.inflate(context.layoutInflater)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            setCancelable(false)
        }
        binding.tvDetailCancel.setOnClickListener {
            dialog.dismiss()
        }

        binding.tvDetailDelete.setOnClickListener {
            dialog.dismiss()
            context.finish()
        }
        dialog.show()
    }


}