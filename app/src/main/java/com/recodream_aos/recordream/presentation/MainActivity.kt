package com.recodream_aos.recordream.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityMainBinding
import com.recodream_aos.recordream.presentation.home.HomeFragment
import com.recodream_aos.recordream.presentation.mypage.MypageActivity
import com.recodream_aos.recordream.presentation.record.RecordActivity
import com.recodream_aos.recordream.presentation.search.SearchActivity
import com.recodream_aos.recordream.presentation.storagy.fragment.StorageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickBottomMenu()
        setClickEvents()
    }

    private fun clickBottomMenu() {
        supportFragmentManager.beginTransaction().add(R.id.fcv_main_navhostfragment, HomeFragment())
            .commit()
        binding.bnvMainCustomnav.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.menu_home -> HomeFragment()
                    else -> StorageFragment()
                },
            )
            true
        }
        binding.bnvMainCustomnav.setOnItemReselectedListener {}
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fcv_main_navhostfragment, fragment)
            .commit()
    }

    private fun setClickEvents() {
        binding.ivMainMypage.setOnClickListener { startActivity(MypageActivity.getIntent(this)) }
        binding.ivNaviWriteBtn.setOnClickListener { startActivity(RecordActivity.getIntent(this)) }
        binding.ivMainSearch.setOnClickListener { startActivity(SearchActivity.getIntent(this)) }
    }
}
