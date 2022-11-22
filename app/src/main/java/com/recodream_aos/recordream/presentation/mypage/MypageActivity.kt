package com.recodream_aos.recordream

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.recodream_aos.recordream.databinding.ActivityMypageBinding
import com.recodream_aos.recordream.presentation.login.LoginActivity
import com.recodream_aos.recordream.presentation.mypage.MypageBottomSheetFragment
import com.recodream_aos.recordream.presentation.mypage.MypageViewModel
import com.recodream_aos.recordream.util.CustomDialog
import com.recodream_aos.recordream.util.RecorDreamFireBaseMessagingService
import com.recodream_aos.recordream.util.shortToast

class MypageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageBinding
    private val myPageBottomSheetFragment = MypageBottomSheetFragment()
    private val firebase = RecorDreamFireBaseMessagingService()
    private val mypageViewModel by viewModels<MypageViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClick()
        initFirebase()

    }

    private val firebaseToken: TextView by lazy { findViewById(R.id.tv_fire) }

    private fun initFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseToken.text = task.result
            }
        }
    }

    private fun setOnClick() {
        binding.tvMypageDeleteAccount.setOnClickListener { showDialog() }
        binding.btnMypageLogout.setOnClickListener { outLogin() }
        binding.ivMypageEditName.setOnClickListener { editName() }
        switchOnClick()
        binding.ivMypageBack.setOnClickListener { finish() }

    }

    private fun editName() {
        binding.ivMypageEditName.setOnClickListener {
            binding.edtMypageName.isEnabled
        }
    }

    private fun showNicknameWarning() {
        if (binding.edtMypageName.text.isNullOrBlank()) {
            shortToast(R.string.mypage_name_warning)
        }
    }

    private fun showDialog() {
        val dialog: CustomDialog
        dialog = CustomDialog(this@MypageActivity)
        dialog.mypageShowDeleteDialog(R.layout.custom_mypage_dialog)
    }

    private fun outLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }

    private fun createBottomSheet() {
        myPageBottomSheetFragment.show(supportFragmentManager, myPageBottomSheetFragment.tag)
    }

    private fun switchOnClick() {
        binding.switchMypagePushAlam.setOnCheckedChangeListener { compoundButton, onSwitch ->
            if (onSwitch) {
                firebase
                createBottomSheet()
            } else {

            }
        }
    }
}

