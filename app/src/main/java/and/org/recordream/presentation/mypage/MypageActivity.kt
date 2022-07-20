package and.org.recordream.presentation.mypage

import and.org.recordream.R
import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.request.RequestMypagePutTime
import and.org.recordream.data.remote.response.ResponseWrapper
import and.org.recordream.databinding.ActivityMypageBinding
import and.org.recordream.util.CustomDialog
import and.org.recordream.util.enqueueUtil
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import java.time.LocalDateTime

class MypageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageBinding
    private lateinit var dialog: CustomDialog
    private val myPageBottomSheetFragment = MypageBottomSheetFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)

        setContentView(binding.root)

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
                settinTime()
                initNetwork()
            }
        }
    }

    private fun showDialog() {
        binding.tvMypageWithdrawl.setOnClickListener {
            dialog = CustomDialog(this@MypageActivity)
            dialog.showDropOutDialog(R.layout.detail_delete_dialog)
        }

    }

    private fun editClick() {
        binding.ivMypageEdit.setOnClickListener {
            binding.edMypageEditnickname.visibility = View.VISIBLE
            binding.tvMypageNickname.visibility = View.INVISIBLE
        }
    }

    private fun backClick() {   //뒤로가기
        binding.ivMypageBackbtn.setOnClickListener {
            finish()
        }
    }

    private fun settinTime() {
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
                Log.d("data","${it.status}")
            }
        )
    }
}








