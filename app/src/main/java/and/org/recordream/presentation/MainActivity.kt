package and.org.recordream.presentation

import and.org.recordream.R
import and.org.recordream.databinding.ActivityMainBinding
import and.org.recordream.presentation.mypage.MypageActivity
import and.org.recordream.presentation.search.SearchActivity
import and.org.recordream.presentation.write.WriteActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickEvent()
        initNav()

    }

    private fun initNav() {
        NavigationUI.setupWithNavController(
            binding.bnvMainCustomnav,
            findNavController(R.id.fcv_main_navhostfragment)
        )
    }

    private fun clickEvent() {
        with(binding) {
            ivMainMypage.setOnClickListener {
                val intent = Intent(this@MainActivity, MypageActivity::class.java)
                startActivity(intent)
            }

            ivMainWritebtn.setOnClickListener {
                val intent = Intent(this@MainActivity, WriteActivity::class.java)
                startActivity(intent)
            }
            ivMainSearch.setOnClickListener {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
