package com.recodream_aos.recordream.presentation.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
    private var nickname: String = ""
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
            isShow.observe(this@MypageActivity) { item ->
                binding.tvMypageSettitngTimeDescription.text = item
            }
            userName.observe(this@MypageActivity) { name ->
                Log.d("userName", "observe: $name")
                if (name.toString().isNullOrBlank()) {
                    Toast.makeText(
                        this@MypageActivity,
                        R.string.mypage_name_warning,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    binding.edtMypageName.setText(name)
                    putUserName()
                }
            }
            userEmail.observe(this@MypageActivity) { email ->
                binding.tvMypageEmail.text = email
            }
            alamToggle.observe(this@MypageActivity) { toggle ->
                patchAlamToggle(toggle)
            }
            settingTime.observe(this@MypageActivity) { time ->
                binding.tvMypageSettitngTimeDescription.text = time
            }
            toggleActive.observe(this@MypageActivity) { active ->
                toggleActive(active)
            }
        }
    }

//    private fun checkNameNull(name: String) {
//        when (name.isNullOrBlank()) {
//
//        }
//    }

    private fun setOnClick() {
        binding.tvMypageDeleteAccount.setOnClickListener { showDialog() }
        binding.btnMypageLogout.setOnClickListener { outLogin() }
        binding.ivMypageEditName.setOnClickListener { editName() }
        switchOnClick()
        binding.ivMypageBack.setOnClickListener { finish() }

    }

    private fun editName() {
        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        with(binding) {
            edtMypageName.isEnabled = true
            edtMypageName.requestFocus()
            inputMethodManager.showSoftInput(edtMypageName, 0)
        }
        binding.edtMypageName.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    binding.edtMypageName.clearFocus()
                    inputMethodManager.hideSoftInputFromWindow(binding.edtMypageName.windowToken, 0)
                    binding.edtMypageName.isEnabled = false
                    mypageViewModel.userName.value = binding.edtMypageName.text.toString()
//                    mypageViewModel.editNickName(binding.edtMypageName.text.toString())
                    Log.d("mypage", "2editName: enter클릭했다")

                }
                else ->                 // 기본 엔터키 동작
                    return@OnEditorActionListener false
            }
            Log.d("mypage", "editName: 3")
            true
        })
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
            mypageViewModel.checkAlamToggle(onSwitch)
            if (onSwitch) {
                binding.clMypageSettingTime.setBackgroundResource(R.drawable.recatangle_radius_15dp_mypage_white08)
                binding.clSettingTimeDescription.setOnClickListener { createBottomSheet() }
            } else {
                binding.clMypageSettingTime.setBackgroundResource(R.drawable.recatangle_radius_15dp_mypage)
                binding.tvMypageSettitngTimeDescription.visibility = View.GONE
            }
        }
    }

    private fun toggleActive(isActive: Boolean) {
        with(binding) {
            when (isActive) {
                true -> {
                    switchMypagePushAlam.isChecked = true
                    clMypageSettingTime.setBackgroundResource(R.drawable.recatangle_radius_15dp_mypage_white08)
                    Log.d("toggleActive", "toggleActive: $isActive")
                }
                else -> {
                    Log.d("toggleActive", "toggleActive: $isActive")
                }
            }
        }
    }
}
