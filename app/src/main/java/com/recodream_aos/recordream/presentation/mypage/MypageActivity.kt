package com.recodream_aos.recordream.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.ActivityMypageBinding
import com.recodream_aos.recordream.presentation.login.LoginActivity
import com.recodream_aos.recordream.util.CustomDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageBinding
    private val myPageBottomSheetFragment = MypageBottomSheetFragment()
    private val mypageViewModel by viewModels<MypageViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClick()
        mypageDataObserver()
        mypageViewModel.getUser()
    }

    private fun mypageDataObserver() {
        with(mypageViewModel) {
            isShow.observe(this@MypageActivity, Observer { item ->
//                showSettingTime()
                binding.tvMypageSettitngTimeDescription.text = item
            })
            userName.observe(this@MypageActivity, Observer { name ->
                binding.edtMypageName.setText(name)
            })
            userEmail.observe(this@MypageActivity, Observer { email ->
                binding.tvMypageEmail.text = email
                Log.d("mypageactivity", "mypageDataObserver: $email")
            })
        }
    }

    private fun setOnClick() {
        binding.tvMypageDeleteAccount.setOnClickListener { showDialog() }
        binding.btnMypageLogout.setOnClickListener { outLogin() }
        binding.ivMypageEditName.setOnClickListener { editName() }
        switchOnClick()
        binding.ivMypageBack.setOnClickListener { finish() }

    }

    private fun showSettingTime() {
//        val settingTime = getString(
//            R.string.mypage_setting_time_description,
//            mypageViewModel.amOrPm.value,
//            mypageViewModel.hour.value,
//            mypageViewModel.minute.value
//        )
//        _hour.value = String.format("%02d", h)
        binding.tvMypageSettitngTimeDescription.text = String.format(
            "%s %d:%d",
            mypageViewModel.amOrPm.value,
            mypageViewModel.hour.value,
            mypageViewModel.minute.value
        )
    }

    private fun editName() {
        binding.ivMypageEditName.setOnClickListener {
            binding.edtMypageName.isEnabled
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
                createBottomSheet()
                binding.clMypageSettingTime.setBackgroundResource(R.drawable.recatangle_radius_15dp_mypage_white08)
                binding.clMypageDreamPush.setOnClickListener { createBottomSheet() }
            } else {
                binding.clMypageSettingTime.setBackgroundResource(R.drawable.recatangle_radius_15dp_mypage)
                binding.tvMypageSettitngTimeDescription.visibility = View.GONE
            }
        }
    }
}
