package com.recodream_aos.recordream.presentation.storagy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.ActivityStoragyBinding
import com.recodream_aos.recordream.presentation.storagy.fragment.StoragyGridFragment
import com.recodream_aos.recordream.presentation.storagy.fragment.StoragyListFragment

class StoragyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoragyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoragyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayFragment()

    }
    private fun initEmotionAdapter(){

    }

    private fun displayFragment() {
        val listFragment = StoragyListFragment()
        val galleryFragment = StoragyGridFragment()
        binding.ivStoreGrid.isSelected = true
        supportFragmentManager.beginTransaction()
            .add(R.id.fg_store_container, StoragyGridFragment())
            .commit()

        binding.ivStoreGrid.setOnClickListener {
            Log.d("storage", "displayFragment: grid")
            binding.ivStoreGrid.isSelected = true
            binding.ivStoreList.isSelected = false

            changeFragment(galleryFragment)
        }
        binding.ivStoreList.setOnClickListener {
            Log.d("storage", "displayFragment: list")
            binding.ivStoreList.isSelected = true
            binding.ivStoreGrid.isSelected = false
            changeFragment(listFragment)
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fg_store_container, fragment)
            .commit()
    }

}