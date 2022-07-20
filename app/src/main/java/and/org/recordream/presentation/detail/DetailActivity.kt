package and.org.recordream.presentation.detail

import and.org.recordream.data.remote.request.RequestDetailDreamRecord
import and.org.recordream.data.remote.response.enqueueUtil
import and.org.recordream.databinding.ActivityDetailBinding
import and.org.recordream.util.CustomDialog
import and.org.recordream.util.ServiceCreator
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
        initBackButton()
        initNetwork()
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

    private fun initBackButton() {
        binding.ivArrowLeft.setOnClickListener {
            finish()
        }
    }
    private fun initNetwork() {
        val requestDetail = RequestDetailDreamRecord(
            recordId = "62d16e7fe8b4508dbca5ead6"
        )

        val call = ServiceCreator.recorDreamService.getDetailRecord(requestDetail.recordId)

        call.enqueueUtil(
            onSuccess = {

            },
            onError = {

            }
        )
    }
}
