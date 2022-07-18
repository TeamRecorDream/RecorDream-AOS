package and.org.recordream.presentation.mypage

import and.org.recordream.R
import and.org.recordream.databinding.ActivityMypageBinding
import and.org.recordream.util.CustomDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MypageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageBinding
    private lateinit var dialog: CustomDialog
    // private val myPageBottomSheetFragment = MypageBottomSheetFragment()

    var isSwitch: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.scMypageSwitchbtn.setOnClickListener {

            //createBottomSheet()
        }

        showDialog()
    }

//    private fun createBottomSheet() {
//        myPageBottomSheetFragment.show(supportFragmentManager, myPageBottomSheetFragment.tag)
//    }


    private fun showDialog() {
        binding.tvMypageWithdrawl.setOnClickListener {
            dialog = CustomDialog(this)
            dialog.showDropOutDialog(R.layout.detail_delete_dialog)
        }
    }
}








