package and.org.recordream.presentation.write

import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.request.RequestWrite
import and.org.recordream.databinding.ActivityWriteBinding
import and.org.recordream.databinding.FragmentDetailBottomSheetBinding
import and.org.recordream.presentation.detail.DetailActivity
import and.org.recordream.util.enqueueUtil
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.util.*

class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBinding
    private lateinit var detailBottomSheet: FragmentDetailBottomSheetBinding
    private val voiceFragment = VoiceRecordFragment()

    var emt: Int = 0
    var dreamcolor: Int = 0
    var dreamgnre = mutableSetOf<Int>(10)
    var dreamgnreTest = mutableSetOf<Int>(10,3,5)
    var gnrecount = 0
    val currenttime = LocalDateTime.now()
    var dateString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)

        clickSave("1") //저장버튼
        voiceClick()
        checkTunderExplanation()
        emotionClick()
        colorClick()
//        clickEmotion()
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

    private fun backClick() {
        binding.ivWriteBack.setOnClickListener {
            finish()
        }
    }

    private fun clickSave(id: String) {   //저장버튼 클릭 시
        binding.btnWriteSave.setOnClickListener {
            if (!binding.btnWriteSave.isSelected) {
                Toast.makeText(this@WriteActivity, "꿈의 제목을 입력주세요.", Toast.LENGTH_SHORT).show()
            } else {
//                hideKeyboard()
                writeInitNetwork()
                val intent = Intent(this@WriteActivity, DetailActivity::class.java)
                intent.apply {
                    putExtra("id", id)
                }
                Log.d("id", "$id")
                startActivity(intent)
                finish()
            }
        }
    }

//    private fun hideKeyboard(view: View) {    키보드 들어가게 하는거
//        val inputMethodManager =
//            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
//    }

    private fun dateClick() {
        binding.clWriteDay.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    dateString = "${year}-${month + 1}-${dayOfMonth}"
                    binding.tvWriteDetailday.text = dateString
                }
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
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

    private fun writeInitNetwork() {
        Log.d("title", "$title ")
        val requestWrite = RequestWrite(
            title = binding.tvWriteEditdream.text.toString(),
            date = "$currenttime",
            content = binding.etWriteContent.text.toString(),
            emotion = emt,
            dream_color = 3,
            genre = mutableSetOf(1,2,4,),
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
                Log.d("여기 wirte임 data", "${it.status}")
            },
            onError = {
                Log.d("write error", "writeInitNetwork: ")
            }
        )
    }

    private fun dreamgnre() {   //장르 서버 연동
        Log.d("gnre", "gnrecount: $gnrecount")
        binding.btnWriteComedy.setOnClickListener {
            dreamgnre.add(0)
            binding.btnWriteComedy.isSelected = true
//            binding.ivDreamRed.
            Log.d("gnre", "dreamgnre: $dreamgnre")
            gnrecount++
        }
        binding.btnWriteRomense.setOnClickListener {
            var emotion = 0
            emotion++
            if (emotion % 2 == 1) {
                dreamgnre.add(1)
                binding.btnWriteRomense.isSelected = true
            } else {
                dreamgnre.remove(1)
                binding.btnWriteRomense.isSelected = false
            }
            gnrecount++
            if (gnrecount > 2) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteAction.setOnClickListener {
            var emotion = 0
            emotion++
            if (emotion % 2 == 1) {
                dreamgnre.add(2)
                binding.btnWriteAction.isSelected = true
            } else {
                dreamgnre.remove(2)
                binding.btnWriteAction.isSelected = false
            }
            gnrecount++
            if (gnrecount > 2) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteThrill.setOnClickListener {
            var emotion = 0
            emotion++
            if (emotion % 2 == 1) {
                dreamgnre.add(3)
                binding.btnWriteThrill.isSelected = true
            } else {
                dreamgnre.remove(3)
                binding.btnWriteThrill.isSelected = false
            }
            gnrecount++
            if (gnrecount > 2) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteMystery.setOnClickListener {
            var emotion = 0
            emotion++
            if (emotion % 2 == 1) {
                dreamgnre.add(4)
                binding.btnWriteMystery.isSelected = true
            } else {
                dreamgnre.remove(4)
                binding.btnWriteMystery.isSelected = false
            }
            gnrecount++
            if (gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteFear.setOnClickListener {
            var emotion = 0
            emotion++
            if (emotion % 2 == 1) {
                dreamgnre.add(5)
                binding.btnWriteFear.isSelected = true
            } else {
                dreamgnre.remove(5)
                binding.btnWriteFear.isSelected = false
            }
            gnrecount++
            if (gnrecount > 2) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnWriteSf.setOnClickListener {
            var emotion = 0
            emotion++
            if (emotion % 2 == 1) {
                dreamgnre.add(6)
                binding.btnWriteSf.isSelected = true
            } else {
                dreamgnre.remove(6)
                binding.btnWriteSf.isSelected = false
            }
            gnrecount++
            if (gnrecount > 2) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteFantasy.setOnClickListener {
            var emotion = 0
            emotion++
            if (emotion % 2 == 1) {
                dreamgnre.add(7)
                binding.btnWriteFantasy.isSelected = true
            } else {
                dreamgnre.remove(7)
                binding.btnWriteFantasy.isSelected = false
            }
            gnrecount++
            if (gnrecount > 2) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteFamily.setOnClickListener {
            var emotion = 0
            emotion++
            if (emotion % 2 == 1) {
                dreamgnre.add(8)
                binding.btnWriteFamily.isSelected = true
            } else {
                dreamgnre.remove(8)
                binding.btnWriteFamily.isSelected = false
            }
            gnrecount++
            if (gnrecount > 2) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteEtc.setOnClickListener {
            var emotion = 0
            emotion++
            if (emotion % 2 == 1) {
                dreamgnre.add(9)
                binding.btnWriteEtc.isSelected = true
            } else {
                dreamgnre.remove(9)
                binding.btnWriteEtc.isSelected = false
            }
            gnrecount++
            if (gnrecount > 2) {
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
                emt = 1
            }
            ivEmotionShocked.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = true
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = false
                emt = 2
            }
            ivEmotionLove.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = true
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = false
                emt = 3
            }
            ivEmotionShy.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = true
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = false
                emt = 4
            }
            ivEmotionSad.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = true
                ivEmotionAngry.isSelected = false
                emt = 5
            }
            ivEmotionAngry.setOnClickListener {
                ivEmotionJoy.isSelected = false
                ivEmotionShocked.isSelected = false
                ivEmotionLove.isSelected = false
                ivEmotionShy.isSelected = false
                ivEmotionSad.isSelected = false
                ivEmotionAngry.isSelected = true
                emt = 6
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