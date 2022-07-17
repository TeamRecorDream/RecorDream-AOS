package and.org.recordream.presentation.write

import and.org.recordream.databinding.ActivityWriteBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cal = Calendar.getInstance() // 현재시각 기입 + 요일
        var day = ""
        var num = cal.get(Calendar.DAY_OF_WEEK)
        when (num) {
            1 -> day = ". SUN"
            2 -> day = ". MON"
            3 -> day = ". TUE"
            4 -> day = ". WED"
            5 -> day = ". THU"
            6 -> day = ". FRI"
            7 -> day = ". SAT"
        }

//        with(binding) {
//            tvWriteAddonelinebtn.visibility = View.INVISIBLE
//            tvWriteDatepickerbtn.text =
//                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + day
//        }
//    }

    }
}