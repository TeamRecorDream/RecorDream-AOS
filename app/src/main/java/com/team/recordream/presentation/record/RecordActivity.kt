package com.team.recordream.presentation.record // ktlint-disable package-name

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.team.recordream.R
import com.team.recordream.base.BindingActivity
import com.team.recordream.databinding.ActivityRecordBinding
import com.team.recordream.presentation.document.DocumentActivity
import com.team.recordream.presentation.record.adapter.RecordAdapter
import com.team.recordream.presentation.record.recording.RecordBottomSheetFragment
import com.team.recordream.util.State.DISCONNECT
import com.team.recordream.util.State.IDLE
import com.team.recordream.util.State.INVALID
import com.team.recordream.util.State.VALID
import com.team.recordream.util.anchorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
        observeTitleForSaveActivation()
    }

    private fun observeTitleForSaveActivation() {
        collectWithLifecycle(recordViewModel.title) {
            recordViewModel.updateSaveButtonEnabled()
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
                true -> initNetwork()
                false -> binding.btnRecordSave.anchorSnackBar(R.string.tv_record_warning_save)
            }
        }
    }

    private fun initNetwork() {
        recordViewModel.postRecord()

        collectWithLifecycle(recordViewModel.stateOfSavingRecord) { result ->
            when (result) {
                is VALID -> navigateToDocumentView()
                is INVALID -> Log.e("RecordActivity", "에러 핸들링 필요")
                is DISCONNECT -> Log.e("RecordActivity", "에러 핸들링 필요")
                is IDLE -> Log.e("RecordActivity", "DEFAULT")
            }
        }
    }

    private fun navigateToDocumentView() {
        DocumentActivity.getIntent(this, recordViewModel.voiceId.value)
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
        crossinline action: (T) -> Unit,
    ) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest { value ->
                    action(value)
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, RecordActivity::class.java)
    }
}
