package com.team.recordream.presentation.mypage

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.team.recordream.R
import com.team.recordream.databinding.ActivityMypageBinding
import com.team.recordream.presentation.login.LoginActivity
import com.team.recordream.util.RecorDreamFireBaseMessagingService
import com.team.recordream.util.customview.CustomDialog
import com.team.recordream.util.makeSnackBar
import com.team.recordream.util.shortToastByInt
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MypageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageBinding
    private val mypageViewModel by viewModels<MypageViewModel>()

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
//                    shortToastByInt(R.string.mypage_alarm_yes)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClick()
        lodingObserve()
        mypageDataObserver()
        mypageViewModel.getUser()
        mypageViewModel.switchState = getSharedPreferences(SWITCH, MODE_PRIVATE)
        saveSwitchActive()
        setBackGround(binding.switchMypagePushAlam.isChecked)
        sendSdkNotify()
    }

    private fun mypageDataObserver() {
        with(mypageViewModel) {
            userName.observe(this@MypageActivity) { name ->
                if (name.isNullOrBlank()) {
                    if (binding.tvMypageEmail.text.isNullOrEmpty()) {
                        binding.edtMypageName.makeSnackBar(R.string.network_error)
                        return@observe
                    }
                    shortToastByInt(R.string.mypage_name_warning)
                    binding.edtMypageName.setText("")
                } else {
                    binding.edtMypageName.setText(name.toString())
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
                clickSaveBtnOnBottomSheet(save)
            }
            isShow.observe(this@MypageActivity) {
                binding.tvMypageSettitngTimeDescription.text = it
            }
        }
    }

    private fun lodingObserve() {
        mypageViewModel.state.observe(this) { state ->
            when (state) {
                is MypageViewModel.ViewState.Loading -> {
                    binding.lvStorageLottieLoading.playAnimation()
                    binding.clLoadingBackground.visibility = View.VISIBLE
                    binding.lvStorageLottieLoading.visibility = View.VISIBLE
                }

                is MypageViewModel.ViewState.Success -> {
                    binding.lvStorageLottieLoading.pauseAnimation()
                    binding.lvStorageLottieLoading.visibility = View.INVISIBLE
                    binding.clLoadingBackground.visibility = View.INVISIBLE
                }

                is MypageViewModel.ViewState.Idle -> {}
            }
        }
    }

    private fun clickSaveBtnOnBottomSheet(isSave: Boolean?) {
        if (isSave == true) {
            setBackGround(true)
            mypageViewModel.switchState.edit {
                putBoolean(
                    ALARM,
                    binding.switchMypagePushAlam.isChecked,
                )
            }
        } else {
            if (mypageViewModel.switchState.getBoolean(ALARM, false)) {
                return
            }
            binding.switchMypagePushAlam.isChecked = false
        }
    }

    private fun setOnClick() {
        binding.tvMypageDeleteAccount.setOnClickListener { showDialog() }
        binding.btnMypageLogout.setOnClickListener { outLogin() }
        binding.ivMypageEditName.setOnClickListener {
            editName()
        }
        switchOnClick()
        binding.ivMypageBack.setOnClickListener { finish() }
    }

    private fun editName() {
        binding.edtMypageName.setSelection(binding.edtMypageName.length())
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
                        Timber.d("2번 클릭했다")
                    }

                    else -> // 기본 엔터키 동작
                        return@OnEditorActionListener false
                }
                Timber.d("mypage", "editName: 3")
                true
            },
        )
    }

    private fun showDialog() {
        val dialog = CustomDialog(this@MypageActivity)
        dialog.myPageShowDeleteDialog()
        dialog.setOnClickedListener {
            mypageViewModel.deleteUser()
            resetSharedPushAlarm()
            finishAffinity()
        }
    }

    private fun outLogin() {
        mypageViewModel.userLogout()
        val intent = Intent(this, LoginActivity::class.java)
        finishAffinity()
        resetSharedPushAlarm()
        startActivity(intent)
    }

    private fun createBottomSheet() {
        val myPageBottomSheetFragment = MypageBottomSheetFragment()
        myPageBottomSheetFragment.show(supportFragmentManager, myPageBottomSheetFragment.tag)
        myPageBottomSheetFragment.isCancelable = false
    }

    private fun switchOnClick() {
        binding.switchMypagePushAlam.setOnCheckedChangeListener { compoundButton, onSwitch ->
            val storeSwitch = mypageViewModel.switchState.getBoolean(ALARM, false)
            if (!onSwitch) {
                mypageViewModel.switchState.edit { putBoolean(ALARM, false) }
                mypageViewModel.patchAlamToggle(false)
                setBackGround(false)
            }
            if (storeSwitch) {
                return@setOnCheckedChangeListener
            }
            if (onSwitch) {
                mypageViewModel.patchAlamToggle(true)
                createBottomSheet()
            }
        }
    }

    private fun setBackGround(onSwitch: Boolean) {
        if (onSwitch) {
            binding.tvMypageSettitngTimeDescription.visibility = View.VISIBLE
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
            mypageViewModel.setDay = "AM"
            mypageViewModel.setHour = 0
            mypageViewModel.setMinute = 0
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
        binding.switchMypagePushAlam.isChecked =
            mypageViewModel.switchState.getBoolean(ALARM, false)
    }

    private fun resetSharedPushAlarm() {
        val editor = mypageViewModel.switchState.edit()
        editor.remove(ALARM)
        editor.clear()
        editor.commit()
    }

    companion object {
        const val DENIED = "denied"
        const val EXPLAINED = "explained"
        const val ALARM = "ALARM"
        const val SWITCH = "SWITCH"

        fun getIntent(context: Context): Intent =
            Intent(context, MypageActivity::class.java)
    }
}
