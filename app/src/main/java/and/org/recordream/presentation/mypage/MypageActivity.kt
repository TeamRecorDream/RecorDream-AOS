package and.org.recordream.presentation.mypage

import and.org.recordream.databinding.ActivityMypageBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MypageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setonNickNameClick() {
        binding.tvMypageNickname.setOnClickListener {

        }
    }


}