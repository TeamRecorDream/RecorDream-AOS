package com.recodream_aos.recordream.presentation.mypage

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.ActivityMypageBinding
import com.recodream_aos.recordream.presentation.login.LoginActivity
import com.recodream_aos.recordream.util.RecorDreamFireBaseMessagingService
import com.recodream_aos.recordream.util.customview.CustomDialog
import com.recodream_aos.recordream.util.shortToastByInt
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageBinding
    private val mypageViewModel by viewModels<MypageViewModel>()
    private var nickname: String = ""
    lateinit var switch: SharedPreferences

    // 권한 요청용 Activity Callback 객체 만들기
    private val registerForActivityResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val deniedPermissionList = permissions.filter { !it.value }.map { it.key }
            when {
                deniedPermissionList.isNotEmpty() -> {
                    val map = deniedPermissionList.groupBy { permission ->
                        if (shouldShowRequestPermissionRationale(permission)) DENIED else EXPLAINED
                    }
                    map[DENIED]?.let {
                        // 단순히 권한이 거부 되었을 때
                        shortToastByInt(R.string.mypage_alarm_No)
                    }
                    map[EXPLAINED]?.let {
                        // 권한 요청이 완전히 막혔을 때(주로 앱 상세 창 열기)
                        shortToastByInt(R.string.mypage_alarm_else)
                    }
                }

                else -> {
                    // 모든 권한이 허가 되었을 때
                    shortToastByInt(R.string.mypage_alarm_yes)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClick()
        mypageDataObserver()
        mypageViewModel.getUser()
        switch = getSharedPreferences(SWITCH, MODE_PRIVATE)
        saveSwitchActive()
        setBackGround(binding.switchMypagePushAlam.isChecked)
    }

    private fun mypageDataObserver() {
        with(mypageViewModel) {
//            isShow.observe(this@MypageActivity) { item ->
//                binding.tvMypageSettitngTimeDescription.visibility = View.VISIBLE
//                binding.tvMypageSettitngTimeDescription.text = item
//            }
            userName.observe(this@MypageActivity) { name ->
                if (name.toString().isNullOrBlank()) {
                    shortToastByInt(R.string.mypage_name_warning)
                } else {
                    binding.edtMypageName.setText(name)
                    putUserName()
                }
            }
            userEmail.observe(this@MypageActivity) { email ->
                binding.tvMypageEmail.text = email
            }
            settingTime.observe(this@MypageActivity) { time ->
                if (binding.switchMypagePushAlam.isChecked) {
                    binding.tvMypageSettitngTimeDescription.text = time
                    setBackGround(true)
                } else {
                    setBackGround(false)
                }
            }
            isSuccessWithdraw.observe(this@MypageActivity) { success -> }
            saveTime.observe(this@MypageActivity) { save ->
                if (save == true) {
                    setBackGround(true)
                    switch.edit { putBoolean(ALARM, binding.switchMypagePushAlam.isChecked) }
                    mypageViewModel.patchAlamToggle(true)
                } else {
                    if (switch.getBoolean(ALARM, false)) {
                        return@observe
                    }
                    binding.switchMypagePushAlam.isChecked = false
                    mypageViewModel.patchAlamToggle(false)
                }
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
        binding.edtMypageName.setOnEditorActionListener(
            OnEditorActionListener { v, actionId, event ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        binding.edtMypageName.clearFocus()
                        inputMethodManager.hideSoftInputFromWindow(
                            binding.edtMypageName.windowToken,
                            0,
                        )
                        binding.edtMypageName.isEnabled = false
                        mypageViewModel.userName.value = binding.edtMypageName.text.toString()
                        Log.d("mypage", "2editName: enter클릭했다")
                    }

                    else -> // 기본 엔터키 동작
                        return@OnEditorActionListener false
                }
                Log.d("mypage", "editName: 3")
                true
            },
        )
    }

    private fun showDialog() {
        val dialog = CustomDialog(this@MypageActivity)
        dialog.mypageShowDeleteDialog(R.layout.custom_mypage_dialog)
        dialog.setOnClickedListener {
            mypageViewModel.deleteUser()
            finishAffinity()
        }
    }

    private fun outLogin() {
        mypageViewModel.userLogout()
        val intent = Intent(this, LoginActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }

    private fun createBottomSheet() {
        val myPageBottomSheetFragment = MypageBottomSheetFragment()
        myPageBottomSheetFragment.show(supportFragmentManager, myPageBottomSheetFragment.tag)
        myPageBottomSheetFragment.isCancelable = false
    }

    private fun switchOnClick() {
        binding.switchMypagePushAlam.setOnCheckedChangeListener { compoundButton, onSwitch ->
            val storeSwitch = switch.getBoolean(ALARM, false)
            if (!onSwitch) {
                switch.edit { putBoolean(ALARM, false) }
                setBackGround(false)
            }
            if (storeSwitch) {
                return@setOnCheckedChangeListener
            }
            if (onSwitch) {
                createBottomSheet()
            }
        }
    }

    private fun setBackGround(onSwitch: Boolean) {
        if (onSwitch) {
            binding.tvMypageSettingTime.setTextColor(getColor(R.color.white))
            binding.clMypageSettingTime.setBackgroundResource(R.drawable.recatangle_radius_15dp_mypage_white08)
            binding.tvMypageSettingTime.setOnClickListener {
                it.isClickable = true
                createBottomSheet()
            }
            binding.tvMypageSettitngTimeDescription.setOnClickListener {
                it.isClickable = true
                createBottomSheet()
            }
        } else {
            binding.clMypageSettingTime.setBackgroundResource(R.drawable.recatangle_radius_15dp_mypage)
            binding.tvMypageSettitngTimeDescription.visibility = View.GONE
            binding.tvMypageSettingTime.setOnClickListener { it.isClickable = false }
            binding.tvMypageSettitngTimeDescription.setOnClickListener { it.isClickable = false }
        }
    }

    private fun sendSdkNotify() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerForActivityResult.launch(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            )
        }
        RecorDreamFireBaseMessagingService()
    }

    private fun saveSwitchActive() {
        binding.switchMypagePushAlam.isChecked = switch.getBoolean(ALARM, false)
    }

    companion object {
        const val DENIED = "denied"
        const val EXPLAINED = "explained"
        const val ALARM = "ALARM"
        const val SWITCH = "SWITCH"
    }
}
