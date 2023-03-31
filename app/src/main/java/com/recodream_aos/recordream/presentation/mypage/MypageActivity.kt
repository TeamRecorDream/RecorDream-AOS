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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.ActivityMypageBinding
import com.recodream_aos.recordream.presentation.login.LoginActivity
import com.recodream_aos.recordream.util.CustomDialog
import com.recodream_aos.recordream.util.RecorDreamFireBaseMessagingService
import com.recodream_aos.recordream.util.shortToast
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
                        shortToast(R.string.mypage_alarm_No)
                    }
                    map[EXPLAINED]?.let {
                        // 권한 요청이 완전히 막혔을 때(주로 앱 상세 창 열기)
                        shortToast(R.string.mypage_alarm_else)
                    }
                }
                else -> {
                    // 모든 권한이 허가 되었을 때
                    shortToast(R.string.mypage_alarm_yes)
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
    }

    override fun onStart() {
        super.onStart()
        saveValueLoad()
    }


    private fun mypageDataObserver() {
        with(mypageViewModel) {
            isShow.observe(this@MypageActivity) { item ->
                binding.tvMypageSettitngTimeDescription.text = item
            }
            userName.observe(this@MypageActivity) { name ->
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
            isSuccessWithdraw.observe(this@MypageActivity) { success ->

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
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    binding.edtMypageName.clearFocus()
                    inputMethodManager.hideSoftInputFromWindow(binding.edtMypageName.windowToken, 0)
                    binding.edtMypageName.isEnabled = false
                    mypageViewModel.userName.value = binding.edtMypageName.text.toString()
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
    }

    private fun switchOnClick() {
        binding.switchMypagePushAlam.setOnCheckedChangeListener { compoundButton, onSwitch ->
            sendSdkNotify()
            mypageViewModel.checkAlamToggle(onSwitch)
            switch.edit { putBoolean(ALARM, binding.switchMypagePushAlam.isChecked) }
            if (onSwitch) {
                binding.clMypageSettingTime.setBackgroundResource(R.drawable.recatangle_radius_15dp_mypage_white08)
                binding.clSettingTimeDescription.setOnClickListener { createBottomSheet() }
            } else {
                binding.clMypageSettingTime.setBackgroundResource(R.drawable.recatangle_radius_15dp_mypage)
                binding.tvMypageSettitngTimeDescription.visibility = View.GONE
            }
        }
    }

    private fun sendSdkNotify() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerForActivityResult.launch(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS)
            )
        }
        RecorDreamFireBaseMessagingService()
    }

    //서버에서 받아온 스위치 true/false처리
    private fun toggleActive(isActive: Boolean) {
//        val switchActive = switch.getBoolean(ALARM, false)
//        if (switchActive != isActive) {
//            binding.switchMypagePushAlam.isChecked = isActive
//            switch.edit { putBoolean(ALARM, binding.switchMypagePushAlam.isChecked) }
//        }
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

    private fun saveValueLoad() {
        binding.switchMypagePushAlam.isChecked = switch.getBoolean(ALARM, false)
    }

    companion object {
        const val DENIED = "denied"
        const val EXPLAINED = "explained"
        const val ALARM = "ALARM"
        const val SWITCH = "SWITCH"
    }
}
