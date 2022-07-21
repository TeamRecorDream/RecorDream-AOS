package and.org.recordream.presentation.write

import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.request.RequestWrite
import and.org.recordream.databinding.ActivityWriteBinding
import and.org.recordream.databinding.FragmentDetailBottomSheetBinding
import and.org.recordream.presentation.detail.DetailActivity
import and.org.recordream.presentation.detail.DetailBottomSheetFragment
import and.org.recordream.util.RecordreamMapping
import and.org.recordream.util.enqueueUtil
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBinding
    private lateinit var detailBottomSheet:FragmentDetailBottomSheetBinding
    private val voiceFragment = VoiceRecordFragment()

    private var title =""
    private var date = ""
    private var content = ""
    private var emotion = 0
    private var dream_color = 0
    private var genre = listOf<Int>()
    private var note = "null"
    private var voice = "62cdb868c3032f2b7af76531"
    private var writer = "62c9cf068094605c781a2fb9"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)

        clickSave() //저장버튼
        voiceClick()
        checkTunderExplanation()
        wirteInitNetwork()
        emotionClick()
        colorClick()
        clickModify()
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

    private fun clickSave() {   //저장버튼 클릭 시
        binding.btnWriteSave.setOnClickListener {
            if (!binding.btnWriteSave.isSelected) {
                Toast.makeText(this@WriteActivity, "꿈의 제목을 입력주세요.", Toast.LENGTH_SHORT).show()
            } else {
                wirteInitNetwork()
            }
        }
    }

    //제목에 글자 입력시 버튼 바뀌게
    private fun checkTunderExplanation() {
        binding.tvWriteEditdream.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                savebtnActive()
            }
        })
    }
    private fun clickModify(){
        detailBottomSheet.tvDetailBottomEdit.setOnClickListener {
            binding.tvWriteModify.visibility  = View.VISIBLE
            binding.tvWriteWrite.visibility = View.INVISIBLE
        }
    }

    private fun savebtnActive() {
        with(binding) {
            if (tvWriteEditdream.text.toString().isEmpty()) {  //제목이 공백일 경우
                btnWriteSave.isSelected = false
                Toast.makeText(this@WriteActivity, "꿈의 제목을 입력주세요.", Toast.LENGTH_SHORT).show()
            } else {
                btnWriteSave.isSelected = true
            }
        }
    }

    private var pudreamemotion = putDreamDescription()
    private var emotionmaping = RecordreamMapping()
    private fun putDreamDescription() {    //이모지 감정 뭐 클릭 됐는지
        with(binding){
            ivEmotionJoy = emotionmaping.emotionMapping()
            ivEmotionShocked
            ivEmotionLove
            ivEmotionShy
            ivEmotionSad
            ivEmotionAngry
        }
    }

    private fun wirteInitNetwork() {
        val requestWrite = RequestWrite(
            title = binding.tvWriteEditdream.text.toString(),
            date = "2022/07/20 (수)",
            content = binding.etWriteContent.text.toString(),
            emotion = pudreamemotion,
            dream_color = 3,
            genre = listOf(1, 2, 5),
            note = "null",
            voice = "62cdb868c3032f2b7af76531",
            writer = "62c9cf068094605c781a2fb9"
        )
        val call = RecordreamClient.writeService.postRecordDescription(
            1,
            requestWrite
        )
        call.enqueueUtil(
            onSuccess = {
                Log.d("data", "${it.status}")
            }
        )
    }

    //감정 클릭시
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

    //꿈 색상 선택시
    private fun colorClick() {
        with(binding) {
            ivDreamRed.setOnClickListener {
                ivDreamRed.isSelected = true
                ivDreamOrange.isSelected = false
                ivDreamPink.isSelected = false
                ivDreamPurple.isSelected = false
                ivDreamGreen.isSelected = false
                ivDreamBlue.isSelected = false
            }
            ivDreamOrange.setOnClickListener {
                ivDreamRed.isSelected = false
                ivDreamOrange.isSelected = true
                ivDreamPink.isSelected = false
                ivDreamPurple.isSelected = false
                ivDreamGreen.isSelected = false
                ivDreamBlue.isSelected = false
            }
            ivDreamPink.setOnClickListener {
                ivDreamRed.isSelected = false
                ivDreamOrange.isSelected = false
                ivDreamPink.isSelected = true
                ivDreamPurple.isSelected = false
                ivDreamGreen.isSelected = false
                ivDreamBlue.isSelected = false
            }
            ivDreamPurple.setOnClickListener {
                ivDreamRed.isSelected = false
                ivDreamOrange.isSelected = false
                ivDreamPink.isSelected = false
                ivDreamPurple.isSelected = true
                ivDreamGreen.isSelected = false
                ivDreamBlue.isSelected = false
            }
            ivDreamGreen.setOnClickListener {
                ivDreamRed.isSelected = false
                ivDreamOrange.isSelected = false
                ivDreamPink.isSelected = false
                ivDreamPurple.isSelected = false
                ivDreamGreen.isSelected = true
                ivDreamBlue.isSelected = false
            }
            ivDreamBlue.setOnClickListener {
                ivDreamRed.isSelected = false
                ivDreamOrange.isSelected = false
                ivDreamPink.isSelected = false
                ivDreamPurple.isSelected = false
                ivDreamGreen.isSelected = false
                ivDreamBlue.isSelected = true
            }
        }
    }
}
