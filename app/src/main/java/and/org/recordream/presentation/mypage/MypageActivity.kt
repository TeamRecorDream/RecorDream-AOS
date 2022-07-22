package and.org.recordream.presentation.mypage

import and.org.recordream.R
import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.request.RequesMypageNickname
import and.org.recordream.data.remote.request.RequestMypagePutTime
import and.org.recordream.databinding.ActivityMypageBinding
import and.org.recordream.util.CustomDialog
import and.org.recordream.util.enqueueUtil
import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import java.time.LocalDateTime

class MypageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageBinding
    private lateinit var dialog: CustomDialog
    private val myPageBottomSheetFragment = MypageBottomSheetFragment()
    private var token = ""
    private var time = ""
    private var nickname = ""

    private val viewModel by viewModels<MyPageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        pushSwitchClick()
        showDialog()
        editClick()
        backClick()
        firebase()
    }

    private fun createBottomSheet() {
        myPageBottomSheetFragment.show(supportFragmentManager, myPageBottomSheetFragment.tag)
    }

    private fun pushSwitchClick() {
        binding.scMypageSwitchbtn.setOnCheckedChangeListener { compoundButton, onSwitch ->

            //  스위치가 켜지면
            if (onSwitch) {
                createBottomSheet()
                settingTime()
                viewModel.setIsShow(true)
            } else {
                viewModel.setIsShow(false)
            }
        }
    }

    private fun showDialog() {
        binding.tvMypageWithdrawl.setOnClickListener {
            dialog = CustomDialog(this@MypageActivity)
            dialog.showDropOutDialog(R.layout.detail_delete_dialog)
        }

    }
    private fun clickEnter() {  //엔터로 입력
        val str = SpannableStringBuilder("")
        binding.edMypageEditnickname.setOnEditorActionListener { textView, action, event ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard(binding.edMypageEditnickname)
                nickname = binding.edMypageEditnickname.text.toString()
                handled = true
                binding.edMypageEditnickname.text = str
            }
            handled
        }
    }

    private fun hideKeyboard(view: View) {  //키보드 숨기기
        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun editClick() {   //이름 수정
        initNicknameNetwork()
        binding.ivMypageEdit.setOnClickListener {
            binding.edMypageEditnickname.visibility = View.VISIBLE
            binding.tvMypageNickname.visibility = View.INVISIBLE
            focusEditText()
            clickEnter()
        }
//        binding.edMypageEditnickname.visibility =
    }
    private fun focusEditText() {
//        binding.edMypageEditnickname.focusable = 1
        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.edMypageEditnickname, 0)
    }

    private fun backClick() {   //뒤로가기
        binding.ivMypageBackbtn.setOnClickListener {
            finish()
        }
    }

    private fun settingTime() {
        val dateAndtime: LocalDateTime = LocalDateTime.now()
//        binding.tvWriteSelectTime.setText(dateAndtime)
        Log.d("dateAndtime", "settinTime:$dateAndtime ")
    }

    private fun firebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("firebaseToken", "firebase: ${task.result}")
            }
        }
    }

    private fun initNetwork() {
        val requestMypagePutTime = RequestMypagePutTime(
            token = "00000",
            time = "PM 03:10"
        )
        val call = RecordreamClient.mypageService.postPushTime(
            1,
            requestMypagePutTime
        )
        call.enqueueUtil(
            onSuccess = {
                Log.d("data", "${it.status}")
            }
        )
    }

    //이름 수정 서버
    private fun initNicknameNetwork() {
        val requesMypageNickname = RequesMypageNickname(
            nickname = nickname
        )
        val call = RecordreamClient.mypageEditNickname.putEditNickname(
            1,
            requesMypageNickname
        )
    }
}

//private fun clickEnter() {
//        binding.edMypageEditnickname.setOnEditorActionListener { textView, action, event ->
//            var handled = false
//         //   event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER
//            if (action == EditorInfo.IME_ACTION_DONE) {
//                nickname = binding.edMypageEditnickname.text.toString()
//                hideKeyboard(binding.edMypageEditnickname)
//                handled = true
//            }
//            handled
//        }
//    }

//    private fun hideKeyboard(editSearch: View) {
//        val inputMethodManager =
//            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(editSearch.windowToken, 0)
//    }







