package and.org.recordream.presentation

import and.org.recordream.R
import and.org.recordream.databinding.ActivityMainBinding
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

        initNav()

    }

    private fun initNav() {
        NavigationUI.setupWithNavController(
            binding.bnvMainCustomnav,
            findNavController(R.id.fcv_main_navhostfragment)
        )
    }
}
