package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
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
        setEmotionClickListener()
        setGenreClickListener()
    }

    private fun setEmotionClickListener() = Emotion.values().map { emotion ->
        clickEmotionSettingValue(emotion)
    }

    private fun setGenreClickListener() = Genre.values().map { genre ->
        clickGenreSettingValue(genre)
    }

    private fun clickEmotionSettingValue(emotion: Emotion) {
        binding.root.findViewById<ImageView>(emotion.viewId).apply {
            setOnClickListener {
                recordViewModel.getSelectedEmotionId(emotion.emotionID)
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
        ).show()
    }
}
