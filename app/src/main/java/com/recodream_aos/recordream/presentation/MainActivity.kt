package com.recodream_aos.recordream.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityMainBinding
import com.recodream_aos.recordream.presentation.home.HomeFragment
import com.recodream_aos.recordream.presentation.storagy.fragment.StorageFragment
import com.recodream_aos.recordream.presentation.storagy.fragment.StoragyListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTransaction()
    }

    private fun initTransaction() {
        supportFragmentManager.beginTransaction().add(R.id.fcv_main_navhostfragment, HomeFragment())
            .commit()
        binding.bnvMainCustomnav.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.menu_home -> HomeFragment()
                    else -> StorageFragment()
                }
            )
            true
        }
        binding.bnvMainCustomnav.setOnItemReselectedListener {}
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_main_navhostfragment, fragment)
            .commit()
    }

//    private fun setOnClick() {
//        binding.ivMainMypage.setOnClickListener {
//            val intent = Intent(this, MypageActivity::class.java)
//            startActivity(intent)
//        }
//        binding.ivMainSearch.setOnClickListener {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fcv_main_navhostfragment, StoragyListFragment()).commit()
//        }
//    }

}
