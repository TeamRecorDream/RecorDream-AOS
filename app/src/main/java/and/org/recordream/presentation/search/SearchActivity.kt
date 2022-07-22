package and.org.recordream.presentation.search

import and.org.recordream.databinding.ActivitySearchBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)



        setContentView(binding.root)
    }


}