package com.team.recordream.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IntegerRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.team.recordream.R
import com.team.recordream.databinding.ActivityMainBinding
import com.team.recordream.presentation.MainActivity.FragmentType.HOME
import com.team.recordream.presentation.MainActivity.FragmentType.STORAGE
import com.team.recordream.presentation.common.BindingActivity
import com.team.recordream.presentation.home.HomeFragment
import com.team.recordream.presentation.mypage.MypageActivity
import com.team.recordream.presentation.record.RecordActivity
import com.team.recordream.presentation.search.SearchActivity
import com.team.recordream.presentation.storagy.fragment.StorageFragment
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
        replaceFragment<HomeFragment>()

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
        binding.ivNaviWriteBtn.setOnClickListener {
            val homeFragment =
                supportFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)
            if (homeFragment is HomeFragment) {
                homeFragment.binding.vpHome.setCurrentItem(0, false)
            }
            startActivity(
                RecordActivity.getIntent(
                    this,
                    RecordActivity.CREATE_MODE,
                    null
                )
            )
        }
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
