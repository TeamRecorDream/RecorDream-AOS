package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.children
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityRecordBinding
import com.recodream_aos.recordream.presentation.record.recording.RecordBottomSheetFragment

class RecordActivity : BindingActivity<ActivityRecordBinding>(R.layout.activity_record) {
    private val recordViewModel: RecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        setClickListener()
    }

    private fun initViewModel() {
        binding.viewModel = recordViewModel
        binding.lifecycleOwner = this
    }

    private fun setClickListener() {
        with(binding) {
            clRecordDateBtn.setOnClickListener { initDatePickerDialog() }
            clRecordRecordBtn.setOnClickListener { initRecordBottomSheetDialog() }
        }
        clickEmotionSettingValue()
        setGenreClickListener()
    }

    private fun setGenreClickListener() = Genre.values().map { genre ->
        clickGenreSettingValue(genre)
    }

    // 텍스트박스 색깔 바꾸어줄것
    // 장르도 똑같은 코드 적용해줄것
    // 리팩토링한번 진행
    // 버튼 색상바꾸어줄것
    // 장르 3개이면 쇼트메시지 출력해줄것
    // 멀티파트 찐 시작

    private fun clickEmotionSettingValue() {
        var beforeState = 0
        binding.clRecordEmotion.children.forEachIndexed { emotionIndex, view ->
            view.setOnClickListener { emotion ->
                recordViewModel.getSelectedEmotionId(emotionIndex)

                if (emotionIndex == beforeState) {
                    emotion.isSelected = !emotion.isSelected
                    beforeState = emotionIndex
                    return@setOnClickListener
                }
                binding.clRecordEmotion.getChildAt(beforeState).isSelected = false
                emotion.isSelected = true

                Log.d("123123", recordViewModel.emotionId.value.toString())

                beforeState = emotionIndex
            }
        }
    }

    private fun clickGenreSettingValue(genre: Genre) {
        binding.root.findViewById<TextView>(genre.viewId).apply {
            setOnClickListener { view ->
                if (recordViewModel.genre.value.size == 3) {
                    if (view.isSelected) view.isSelected = false
                    recordViewModel.getSelectedGenreId(genre.genreId)
                    return@setOnClickListener
                }
                recordViewModel.getSelectedGenreId(genre.genreId)
                view.isSelected = !view.isSelected
            }
        }
    }

    private fun initRecordBottomSheetDialog() =
        RecordBottomSheetFragment()
            .show(
                supportFragmentManager,
                RecordBottomSheetFragment().tag,
            )

    private fun initDatePickerDialog() {
        val cal = Calendar.getInstance()

        DatePickerDialog(
            this,
            recordViewModel.initDate(),
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH),
        ).apply { datePicker.maxDate = System.currentTimeMillis() }
            .show()
    }
}
