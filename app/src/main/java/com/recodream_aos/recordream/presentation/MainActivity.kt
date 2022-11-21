package com.recodream_aos.recordream.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.recodream_aos.recordream.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        clickEvent()
//        initNav()
    }

//    private fun initNav() {
//        NavigationUI.setupWithNavController(
//            binding.bnvMainCustomnav,
//            findNavController(R.id.fcv_main_navhostfragment)
//        )
//    }

//    private fun clickEvent() {
//        with(binding) {
//            ivMainMypage.setOnClickListener {
//                val intent = Intent(this@MainActivity, MypageActivity::class.java)
//                startActivity(intent)
//            }

//            ivMainWritebtn.setOnClickListener {
//                val intent = Intent(this@MainActivity, WriteActivity::class.java)
//                startActivity(intent)
//            }
//            ivMainSearch.setOnClickListener {
//                val intent = Intent(this@MainActivity, SearchActivity::class.java)
//                startActivity(intent)
//            }
//        }
//    }
}
