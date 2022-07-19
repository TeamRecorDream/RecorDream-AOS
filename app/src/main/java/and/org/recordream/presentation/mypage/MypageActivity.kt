package and.org.recordream.presentation.mypage

import and.org.recordream.R
import and.org.recordream.databinding.ActivityMypageBinding
import and.org.recordream.presentation.home.HomeFragment
import and.org.recordream.util.CustomDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MypageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageBinding
    private lateinit var dialog: CustomDialog
    private val myPageBottomSheetFragment = MypageBottomSheetFragment()

   // var isSwitch: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        pushSwitchClick()
        showDialog()
        editClick()
        backClick()
    }

    private fun createBottomSheet() {
        myPageBottomSheetFragment.show(supportFragmentManager, myPageBottomSheetFragment.tag)
    }

    private fun pushSwitchClick() {
        binding.scMypageSwitchbtn.setOnCheckedChangeListener { compoundButton, onSwitch ->
            //  스위치가 켜지면
            if (onSwitch) {
                createBottomSheet()
            }
        }
    }

    private fun showDialog() {
        binding.tvMypageWithdrawl.setOnClickListener {
            dialog = CustomDialog(this)
            dialog.showDeleteDialog(R.layout.detail_delete_dialog)
        }

    }

    private fun editClick() {
        binding.ivMypageEdit.setOnClickListener {
            binding.edMypageEditnickname.visibility = View.VISIBLE
            binding.tvMypageNickname.visibility = View.INVISIBLE
        }
    }

    private fun backClick() {
        binding.ivMypageBackbtn.setOnClickListener {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
            finish()
        }
    }
}








