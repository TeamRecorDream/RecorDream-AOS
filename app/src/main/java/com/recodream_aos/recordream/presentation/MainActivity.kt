package com.recodream_aos.recordream.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IntegerRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityMainBinding
import com.recodream_aos.recordream.presentation.MainActivity.FragmentType.HOME
import com.recodream_aos.recordream.presentation.MainActivity.FragmentType.STORAGE
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

        setBottomNavigationView()
        setClickEvents()
    }

    private fun setBottomNavigationView() {
        binding.bnvMainCustomnav.selectedItemId = R.id.menu_home
        binding.bnvMainCustomnav.setOnItemSelectedListener(::replaceFragment)
    }

    private fun replaceFragment(item: MenuItem): Boolean {
        when (FragmentType.valueOf(item.itemId)) {
            HOME -> replaceFragment<HomeFragment>()
            STORAGE -> replaceFragment<StorageFragment>()
        }
        return true
    }

    private fun setClickEvents() {
        binding.ivMainMypage.setOnClickListener { startActivity(MypageActivity.getIntent(this)) }
        binding.ivNaviWriteBtn.setOnClickListener { startActivity(RecordActivity.getIntent(this)) }
        binding.ivMainSearch.setOnClickListener { startActivity(SearchActivity.getIntent(this)) }
    }

    private inline fun <reified T : Fragment> replaceFragment() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_main_navhostfragment)
            setReorderingAllowed(true)
        }
    }

    private enum class FragmentType(@IntegerRes private val resId: Int) {
        HOME(R.id.menu_home),
        STORAGE(R.id.menu_storage),
        ;

        companion object {
            fun valueOf(id: Int): FragmentType = values().find { fragmentView ->
                fragmentView.resId == id
            } ?: throw IllegalArgumentException()
        }
    }
}
