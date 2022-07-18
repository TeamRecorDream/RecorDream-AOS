package and.org.recordream.presentation.mypage

import and.org.recordream.R
import and.org.recordream.databinding.ActivityMypageBinding
import and.org.recordream.util.CustomDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class MypageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageBinding
    private lateinit var dialog: CustomDialog

    var isSwitch: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)

        //setOnDreamPushClickEvent()
        initBottomSheet()
        showDialog()
        setContentView(binding.root)
    }

    private fun setonNickNameClick() {
        binding.tvMypageNickname.setOnClickListener {

        }
    }

    private fun initBottomSheet() {
        binding.scMypageDreampushSwitch.setOnClickListener {

            // on below line we are creating a new bottom sheet dialog.
            val dialog = BottomSheetDialog(this)

            //커on below line we are inflating a layout file which we have created.
            val view = layoutInflater.inflate(R.layout.fragment_mypage_timedialog, null)
            //배경 투명하게 해줘서 커스텀한 모양이 보이게
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //외부 화면 누르면 창 자동으로 닫히게
            dialog.setCancelable(true)

            // on below line we are setting
            // content view to our view.
            dialog.setContentView(view)

            // on below line we are calling
            // a show method to display a dialog.
            dialog.show()
        }
    }

    private fun showDialog() {
        binding.tvMypageWithdrawl.setOnClickListener {
            dialog = CustomDialog(this)
            dialog.showDeleteDialog(R.layout.detail_delete_dialog)
        }
    }

}

//private fun createBottomSheet() { // 바텀시트 프래그먼트 생성
//// 전역변수로 이미 초기화 해줬기 때문에 여기는 지워도 됨, 다른 인스턴스 생성
//    writeBottomSheetFragment.show(supportFragmentManager, writeBottomSheetFragment.tag)
//}


//private fun setOnDreamPushClickEvent() {
//
//    //  스위치를 클릭했을때
//    val switch: SwitchCompat = findViewById(R.id.sc_mypage_dreampush_switch)
//    switch.setOnCheckedChangeListener { _, isChecked ->
//        if (isChecked) {
//            // The toggle is enabled
//            val bottomSheet = mypage_timedialog()
//            //  bottomSheet.show(supportFragmentManager, "exampleBottomSheet")
//        } else {
//            // The toggle is disabled
//        }
//    }
//}







