package and.org.recordream.presentation.detail

import and.org.recordream.databinding.ActivityDetailBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initTabLayout()
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
}
