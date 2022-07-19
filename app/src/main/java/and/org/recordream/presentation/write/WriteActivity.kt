package and.org.recordream.presentation.write

import and.org.recordream.databinding.ActivityWriteBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBinding
    private val voiceFragment = VoiceRecordFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)

        voiceClick()
        setContentView(binding.root)
    }

    private fun createBottomSheet() {
        voiceFragment.show(supportFragmentManager, voiceFragment.tag)
    }

    private fun voiceClick(){
        binding.clWriteVoiceRecord.setOnClickListener {
            createBottomSheet()
        }
    }

}