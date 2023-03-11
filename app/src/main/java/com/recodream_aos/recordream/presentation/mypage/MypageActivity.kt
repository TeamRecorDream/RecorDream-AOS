package com.recodream_aos.recordream.presentation.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
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
//                viewModelScope.launch {
//                    mypageUserRepository.postPushAlam(  RequestPushAlam("PM 03:10"))
////                MypageUserRepository
//                }
            }
            userName.observe(this@MypageActivity) { name ->
                binding.edtMypageName.setText(name)
            }
            userEmail.observe(this@MypageActivity) { email ->
                binding.tvMypageEmail.text = email
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
        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        with(binding) {
            edtMypageName.isEnabled = true
            edtMypageName.requestFocus()
            inputMethodManager.showSoftInput(edtMypageName, 0)

        }
        binding.edtMypageName.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            Log.d("mypage", "editName: 1")
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    binding.edtMypageName.clearFocus()
                    inputMethodManager.hideSoftInputFromWindow(binding.edtMypageName.windowToken, 0)
                    binding.edtMypageName.isEnabled = false
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
