package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityRecordBinding
import com.recodream_aos.recordream.presentation.document.DocumentActivity
import com.recodream_aos.recordream.presentation.record.adapter.RecordAdapter
import com.recodream_aos.recordream.presentation.record.recording.RecordBottomSheetFragment
import com.recodream_aos.recordream.util.shortToastByString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecordActivity : BindingActivity<ActivityRecordBinding>(R.layout.activity_record) {
    private val recordViewModel: RecordViewModel by viewModels()
    private val recordAdapter: RecordAdapter by lazy { RecordAdapter(setClickEventOnEmotions()) }

    private fun setClickEventOnEmotions() = object : RecordClickListener {
        override fun setClickEventOnEmotion(emotionId: Int) {
            recordAdapter.updateEmotionState(emotionId)
            recordViewModel.updateSelectedEmotionId(emotionId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        attachAdapter()
        setClickListener()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                recordViewModel.title.collectLatest {
                    recordViewModel.updateSaveButtonEnabled()
                }
            }
        }
    }

    private fun initViewModel() {
        binding.viewModel = recordViewModel
        binding.lifecycleOwner = this
    }

    private fun attachAdapter() {
        binding.rvRecordEmotion.adapter = recordAdapter
        binding.rvRecordEmotion.setHasFixedSize(true)
    }

    private fun setClickListener() {
        binding.clRecordDateBtn.setOnClickListener { initDatePickerDialog() }
        binding.clRecordRecordBtn.setOnClickListener { initRecordBottomSheetDialog() }
        binding.ivRecordClose.setOnClickListener { finish() }
        binding.btnRecordSave.setOnClickListener {
            when (recordViewModel.isSaveEnabled.value) {
                true -> navigateToDocumentView()
                false -> shortToastByString("ss")
            }
        }
    }

    private fun navigateToDocumentView() {
        DocumentActivity.getIntent(this, recordViewModel.postRecord())
        finish()
    }

    private fun initDatePickerDialog() {
        val cal = Calendar.getInstance()

        DatePickerDialog(
            this,
            recordViewModel.updateDate(),
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH),
        ).apply {
            datePicker.maxDate = System.currentTimeMillis()
            show()
        }
    }

    private fun initRecordBottomSheetDialog() {
        RecordBottomSheetFragment().show(supportFragmentManager, RecordBottomSheetFragment().tag)
    }

    private inline fun <T> collectWithLifecycle(
        flow: Flow<T>,
        crossinline action: () -> Unit,
    ) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(lifecycle.currentState) {
                flow.collectLatest { value ->
                    action()
                }
            }
        }
    }
}
