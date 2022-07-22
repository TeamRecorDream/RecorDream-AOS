package and.org.recordream.presentation.write

import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.request.RequestWrite
import and.org.recordream.databinding.ActivityWriteBinding
import and.org.recordream.databinding.FragmentDetailBottomSheetBinding
import and.org.recordream.util.enqueueUtil
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.util.*

class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBinding
    private lateinit var detailBottomSheet: FragmentDetailBottomSheetBinding
    private val voiceFragment = VoiceRecordFragment()

    var tmp: Int = 0
    var dreamcolor: Int = 0
    var dreamgnre = mutableSetOf<Int>(10)
    var gnrecount = 0
    val dateAndtime: LocalDateTime = LocalDateTime.now()
    var dateString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)

        clickSave() //저장버튼
        voiceClick()
        checkTunderExplanation()
//        wirteInitNetwork()
        emotionClick()
        colorClick()
        dateClick()
        dreamgnre()
        backClick()
//        clickModify()
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

    private fun backClick(){
        binding.ivWriteBack.setOnClickListener {
            finish()
        }
    }

    private fun clickSave() {   //저장버튼 클릭 시
        binding.btnWriteSave.setOnClickListener {
            if (!binding.btnWriteSave.isSelected) {
                Toast.makeText(this@WriteActivity, "꿈의 제목을 입력주세요.", Toast.LENGTH_SHORT).show()
            } else {
                wirteInitNetwork()
                finish()
            }
        }
    }

    private fun dateClick(){
        binding.clWriteDay.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateString = "${year}-${month+1}-${dayOfMonth}"
                binding.tvWriteDetailday.setText(dateString)
//                result.text = "날짜/시간 : "+dateString + " / " + timeString
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
            Log.d("dateString", "dateString: $cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)")
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

//    private fun clickModify() {
//        if(detailBottomSheet.tvDetailBottomEdit.isSelected) {
//            binding.tvWriteModify.visibility = View.VISIBLE
//            binding.tvWriteWrite.visibility = View.INVISIBLE
//        }
//    }

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

    private fun wirteInitNetwork() {
        val requestWrite = RequestWrite(
            title = binding.tvWriteEditdream.text.toString(),
            date = dateAndtime,
            content = binding.etWriteContent.text.toString(),
            emotion = tmp,
            dream_color = dreamcolor,
            genre = dreamgnre,
            note = binding.etWriteNotecontent.text.toString(),
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

    private fun clickEmotion() {    //서버 전달을 위한 감정 수
        binding.ivEmotionJoy.setOnClickListener {
            tmp = 1
        }
        binding.ivEmotionShocked.setOnClickListener {
            tmp = 2
        }
        binding.ivEmotionLove.setOnClickListener {
            tmp = 3
        }
        binding.ivEmotionShy.setOnClickListener {
            tmp = 4
        }
        binding.ivEmotionSad.setOnClickListener {
            tmp = 5
        }
        binding.ivEmotionAngry.setOnClickListener {
            tmp = 6
        }
    }

    private fun dreamgnre() {   //장르 서버 연동
        Log.d("gnre", "gnrecount: $gnrecount")
        binding.tvWriteComedy.setOnClickListener {
            dreamgnre.add(0)
            binding.tvWriteComedy.isSelected =true
//            binding.ivDreamRed.
            Log.d("gnre", "dreamgnre: $dreamgnre")
            gnrecount++
        }
        binding.tvWriteRomense.setOnClickListener {
            dreamgnre.add(1)
            gnrecount++
            if ( gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvWriteAction.setOnClickListener {
            dreamgnre.add(2)
            gnrecount++
            if ( gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvWriteThrill.setOnClickListener {
            dreamgnre.add(3)
            gnrecount++
            if ( gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvWriteMystery.setOnClickListener {
            dreamgnre.add(4)
            gnrecount++
            if ( gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvWriteFear.setOnClickListener {
            dreamgnre.add(5)
            gnrecount++
            if ( gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvWriteSf.setOnClickListener {
            dreamgnre.add(6)
            gnrecount++
            if ( gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvWriteFantasy.setOnClickListener {
            dreamgnre.add(7)
            gnrecount++
            if ( gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvWriteFamily.setOnClickListener {
            dreamgnre.add(8)
            gnrecount++
            if ( gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvWriteEtc.setOnClickListener {
            dreamgnre.add(9)
            gnrecount++
            if ( gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }

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
