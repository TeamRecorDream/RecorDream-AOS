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
        emotionClick()
        setContentView(binding.root)
    }

    private fun createBottomSheet() {
        voiceFragment.show(supportFragmentManager, voiceFragment.tag)
    }

    private fun voiceClick() {
        binding.clWriteVoiceRecord.setOnClickListener {
            createBottomSheet()
        }
    }

    private fun emotionClick() {
        with(binding) {
            ivEmotionJoy.setOnClickListener {
                ivEmotionJoy.isSelected = true
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = false
            }
            ivEmotionShocked.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = true
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = false
            }
            ivEmotionLove.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = true
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = false
            }
            ivEmotionShy.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = true
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = false
            }
            ivEmotionSad.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = true
                ivEmotionAngry.isSelected = false
            }
            ivEmotionAngry.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = true
            }
        }
    }

    private fun colorClick() {
        with(binding) {
            ivEmotionJoy.setOnClickListener {
                ivEmotionJoy.isSelected = true
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = false
            }
            ivEmotionShocked.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = true
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = false
            }
            ivEmotionLove.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = true
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = false
            }
            ivEmotionShy.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = true
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = false
            }
            ivEmotionSad.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = true
                ivEmotionAngry.isSelected = false
            }
            ivEmotionAngry.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = true
            }
        }

    }
}
