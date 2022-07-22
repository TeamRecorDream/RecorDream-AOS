package and.org.recordream.presentation.edit

import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.request.RequestWrite
import and.org.recordream.databinding.ActivityEditBinding
import and.org.recordream.databinding.ActivityWriteBinding
import and.org.recordream.databinding.FragmentDetailBottomSheetBinding
import and.org.recordream.presentation.detail.DetailActivity
import and.org.recordream.presentation.write.VoiceRecordFragment
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

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var detailBottomSheet: FragmentDetailBottomSheetBinding
    private val voiceFragment = VoiceRecordFragment()

    var tmp: Int = 0
    var dreamcolor: Int = 0
    var dreamgnre = mutableSetOf<Int>(10)
    var gnrecount = 0
    val currenttime = LocalDateTime.now()
    var dateString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)

        clickSave("1") //저장버튼
        voiceClick()
        checkTunderExplanation()
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

    private fun backClick() {
        binding.ivWriteBack.setOnClickListener {
            finish()
        }
    }

    private fun clickSave(id: String) {   //저장버튼 클릭 시
        binding.btnWriteSave.setOnClickListener {
            if (!binding.btnWriteSave.isSelected) {
                Toast.makeText(this@EditActivity, "꿈의 제목을 입력주세요.", Toast.LENGTH_SHORT).show()
            } else {
//                hideKeyboard()
                writeInitNetwork()
                val intent = Intent(this@EditActivity, DetailActivity::class.java)
                intent.apply {
                    putExtra("id", id)
                }
                Log.d("id값 찍어", "$id")
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
                Toast.makeText(this@EditActivity, "꿈의 제목을 입력주세요.", Toast.LENGTH_SHORT).show()
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
            emotion = tmp,
            dream_color = dreamcolor,
            genre = mutableSetOf(1, 2, 3),
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
            },
            onError = {
                Log.d("error", "writeInitNetwork: ")
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
        binding.btnWriteComedy.setOnClickListener {
            dreamgnre.add(0)
            binding.btnWriteComedy.isSelected = true
//            binding.ivDreamRed.
            Log.d("gnre", "dreamgnre: $dreamgnre")
            gnrecount++
        }
        binding.btnWriteRomense.setOnClickListener {
            dreamgnre.add(1)
            gnrecount++
            if (gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteAction.setOnClickListener {
            dreamgnre.add(2)
            gnrecount++
            if (gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteThrill.setOnClickListener {
            dreamgnre.add(3)
            gnrecount++
            if (gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteMystery.setOnClickListener {
            dreamgnre.add(4)
            gnrecount++
            if (gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteFear.setOnClickListener {
            dreamgnre.add(5)
            gnrecount++
            if (gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnWriteSf.setOnClickListener {
            dreamgnre.add(6)
            gnrecount++
            if (gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteFantasy.setOnClickListener {
            dreamgnre.add(7)
            gnrecount++
            if (gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteFamily.setOnClickListener {
            dreamgnre.add(8)
            gnrecount++
            if (gnrecount > 4) {
                Toast.makeText(this, "꿈의 장르는 최대 3개까지만 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnWriteEtc.setOnClickListener {
            dreamgnre.add(9)
            gnrecount++
            if (gnrecount > 4) {
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