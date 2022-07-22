package and.org.recordream.presentation.search

import and.org.recordream.databinding.ActivitySearchBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)


        binding.ivSearchSearchbtn.setOnClickListener {
            initNetwork()
        }
        setContentView(binding.root)
    }

    private fun initNetwork() {


    }

    private fun setRecyclerView() {

    }


}