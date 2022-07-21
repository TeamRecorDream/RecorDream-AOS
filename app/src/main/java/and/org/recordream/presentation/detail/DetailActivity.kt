package and.org.recordream.presentation.detail

import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.response.ResponseDetailDreamRecord
import and.org.recordream.databinding.ActivityDetailBinding
import and.org.recordream.util.CustomDialog
import and.org.recordream.util.RecordreamMapping
import and.org.recordream.util.enqueueUtil
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailBottomSheetFragment = DetailBottomSheetFragment()
    private lateinit var dialog: CustomDialog

    // lateinit var btnShowBottomSheet: ImageView
    private val recorDreamMapping = RecordreamMapping()

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
        val recordId = intent.getStringExtra("id")
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
//        val requestDetail = RequestDetailDreamRecord(
//            recordId = "62d16e7fe8b4508dbca5ead6"
//        )
        val recordId = "62d7b6f19669f53b6c72a89f"
        Log.d("dddddddddd", "wddddddddd123123ddddd")
        val call = RecordreamClient.recorDreamServicee.getDetailRecord(recordId)

        call.enqueueUtil(
            onSuccess = {
                Log.d("dddddddddd", "${it.status}")

                val data = it.data
                applyData(data)
            },
            onError = {
                Log.d("dddddddddd", "$it")
            }
        )
    }

    private fun applyData(response: ResponseDetailDreamRecord?) {
        val applyEmotion = response?.emotion?.let { recorDreamMapping.matchEmotion(it) }
        val applyTextColor = response?.let { recorDreamMapping.matchTextColor(it.dream_color) }
        val applyGenre = response?.let { recorDreamMapping.genreMapping(it.genre) }
        val applyCardImage = response?.let { recorDreamMapping.matchDetailColor(it.dream_color) }

//        context?.let { ContextCompat.getDrawable(it, R.drawable.logo) }
//          ?.let { binding.ivDetailDreamColor.background = it }
////        binding.ivDetailDreamColor = applyCardImage
//        binding.ivDetailDreamColor.background = applyEmotion.toDrawable()
        if (applyCardImage != null) {
            binding.ivDetailDreamColor.setBackgroundResource(applyCardImage)
        }
        if (applyEmotion != null) {
            binding.ivProfile.setBackgroundResource(applyEmotion)
        }
//
//        binding.ivProfile.setImageDrawable(applyEmotion)

        if (response != null) {
            binding.tvRecordDate.text = response.date
            if (response.genre.size == 1) {
                binding.tvHomeGenre1.text = "#${applyGenre?.get(0)}"
            }
            if (response.genre.size == 2) {
                binding.tvHomeGenre1.text = "#${applyGenre?.get(0)}"
                binding.tvHomeGenre2.text = "#${applyGenre?.get(1)}"
            }
            if (response.genre.size == 3) {
                binding.tvHomeGenre1.text = "#${applyGenre?.get(0)}"
                binding.tvHomeGenre2.text = "#${applyGenre?.get(1)}"
                binding.tvHomeGenre3.text = "#${applyGenre?.get(2)}"
            }
            when (response.genre.size) {
                1 -> {
                    binding.tvHomeGenre1.visibility = View.VISIBLE
                    binding.tvHomeGenre2.visibility = View.INVISIBLE
                    binding.tvHomeGenre3.visibility = View.INVISIBLE
                }
                2 -> {
                    binding.tvHomeGenre1.visibility = View.VISIBLE
                    binding.tvHomeGenre2.visibility = View.VISIBLE
                    binding.tvHomeGenre3.visibility = View.INVISIBLE
                }
                3 -> {
                    binding.tvHomeGenre1.visibility = View.VISIBLE
                    binding.tvHomeGenre2.visibility = View.VISIBLE
                    binding.tvHomeGenre3.visibility = View.VISIBLE
                }
            }
            if (applyTextColor != null) {
                binding.tvHomeGenre1.setTextColor(applyTextColor)
                binding.tvHomeGenre2.setTextColor(applyTextColor)
                binding.tvHomeGenre3.setTextColor(applyTextColor)
            }
        }
    }
}

