package and.org.recordream.presentation.detail

import and.org.recordream.databinding.ActivityDetailBinding
import and.org.recordream.util.CustomDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val detailBottomSheetFragment = DetailBottomSheetFragment()
    private lateinit var dialog: CustomDialog

    // lateinit var btnShowBottomSheet: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // btnShowBottomSheet = binding.ivDotsMore


        initAdapter()
        initTabLayout()
        // initBottomSheet()
        // showDialog()

        binding.ivDotsMore.setOnClickListener { createBottom() }

    }

    private fun createBottom() {
        detailBottomSheetFragment.show(supportFragmentManager, detailBottomSheetFragment.tag)
    }

    private fun initAdapter() {
        val detailViewPagerAdapter = DetailViewPagerAdapter(this)
        binding.vpDetail.adapter = detailViewPagerAdapter
    }

    private fun initTabLayout() {
        val tabLabel = listOf("나의 꿈 기록", "노트")

        TabLayoutMediator(binding.tlDetail, binding.vpDetail) { tab, position ->
            tab.text = tabLabel[position]
        }.attach()
    }

//    private fun initBottomSheet() {
//        btnShowBottomSheet.setOnClickListener {
//
//            // on below line we are creating a new bottom sheet dialog.
//            val dialog = BottomSheetDialog(this)
//            //on below line we are inflating a layout file which we have created.
//            val view = layoutInflater.inflate(R.layout.detail_bottom_sheet, null)
//            //배경 투명하게 해줘서 커스텀한 모양이 보이게
//            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            //외부 화면 누르면 창 자동으로 닫히게
//            dialog.setCancelable(true)
//
//            // on below line we are setting
//            // content view to our view.
//            dialog.setContentView(view)
//
//            // on below line we are calling
//            // a show method to display a dialog.
//            dialog.show()
//        }
//    }

}
