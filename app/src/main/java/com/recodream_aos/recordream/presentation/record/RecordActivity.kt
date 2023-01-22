package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityRecordBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecordActivity : BindingActivity<ActivityRecordBinding>(R.layout.activity_record) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvRecordDateNum.text =
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        binding.clRecordDateBtn.setOnClickListener {
            val cal = Calendar.getInstance()

            val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                binding.tvRecordRecordingTime.text = "$year/$month/$day"
            }
            DatePickerDialog(
                this,
                data,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}
