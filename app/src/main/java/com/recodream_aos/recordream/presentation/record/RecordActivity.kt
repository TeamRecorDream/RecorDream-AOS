package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityRecordBinding
import com.recodream_aos.recordream.presentation.record.recording.RecordBottomSheetFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecordActivity : BindingActivity<ActivityRecordBinding>(R.layout.activity_record) {
    private val recordViewModel: RecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        setClickListener()
        //  collectStateEmotion()
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

    @SuppressLint("ResourceType")
    private fun clickEmotionSettingValue(emotion: Emotion) {
        var beforeState = 2131296406
        binding.root.findViewById<ConstraintLayout>(emotion.viewId).apply {
            setOnClickListener { view ->
                recordViewModel.getSelectedEmotionId(emotion.emotionID)
                Log.d("123123", emotion.viewId.toString())
                binding.root.findViewById<ConstraintLayout>(beforeState).isSelected = false
                Log.d("123123", R.id.cl_record_joy.toString())
                view.isSelected = !view.isSelected

                beforeState = emotion.viewId
            }
        }
    }

    // binding.레이아웃.children.forEach { view ->
    //            // 로직
    //        }

    private fun collectStateEmotion() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                recordViewModel.emotionViewId.collectLatest { selectedEmotion ->
                    Log.d("123123", selectedEmotion.toString())
                    val view = binding.clRecordEmotion.children.map {
                        it.isSelected = true
                    }
                }
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
