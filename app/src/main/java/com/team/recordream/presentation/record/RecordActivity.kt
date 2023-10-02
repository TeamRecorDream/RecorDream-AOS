package com.team.recordream.presentation.record

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.team.recordream.R
import com.team.recordream.databinding.ActivityRecordBinding
import com.team.recordream.presentation.common.BindingActivity
import com.team.recordream.presentation.detail.DetailActivity
import com.team.recordream.presentation.record.adapter.RecordAdapter
import com.team.recordream.presentation.record.model.EmotionState
import com.team.recordream.presentation.record.recording.RecordBottomSheetFragment
import com.team.recordream.util.StateHandler.DISCONNECT
import com.team.recordream.util.StateHandler.IDLE
import com.team.recordream.util.StateHandler.INVALID
import com.team.recordream.util.StateHandler.VALID
import com.team.recordream.util.anchorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecordActivity : BindingActivity<ActivityRecordBinding>(R.layout.activity_record) {
    private val recordViewModel: RecordViewModel by viewModels()
    private val recordAdapter: RecordAdapter by lazy { RecordAdapter(recordViewModel::updateSelectedEmotionId) }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        collectViewState()
        setupBinding()
        attachAdapter()
        setClickListener()
    }

    private fun collectViewState() {
        collectWithLifecycle(recordViewModel.emotion) { emotion ->
            val emotionStateContainer =
                EmotionState.getEmotionContainer(emotion ?: EmotionState.SELECTED_ANYTHING)

            recordAdapter.submitList(emotionStateContainer)
        }

        collectWithLifecycle(recordViewModel.title) { title ->
            recordViewModel.updateSaveButtonEnabled(title)
        }

        collectWithLifecycle(recordViewModel.stateHandlerOfSavingRecord) { result ->
            when (result) {
                is VALID -> navigateToDetailView(result.recordId)
                is INVALID -> Log.e("RecordActivity", "에러 핸들링 필요")
                is DISCONNECT -> Log.e("RecordActivity", "에러 핸들링 필요")
                is IDLE -> Log.e("RecordActivity", "DEFAULT")
            }
        }
    }

    private fun navigateToDetailView(recordId: String) {
        startActivity(DetailActivity.getIntent(this, recordId))
        finish()
    }

    private fun setupBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = recordViewModel
    }

    private fun attachAdapter() {
        binding.rvRecordEmotion.adapter = recordAdapter
        binding.rvRecordEmotion.setHasFixedSize(true)
    }

    private fun setClickListener() {
        binding.clRecordDate.setOnClickListener { initDatePickerDialog() }
        binding.ivRecordClose.setOnClickListener { finish() }
        binding.clRecordRecord.setOnClickListener { initRecordBottomSheetDialog() }
        binding.btnRecordSave.setOnClickListener { createRecord() }
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


    private fun createRecord() {
        when (recordViewModel.isSaveEnabled.value) {
            true -> recordViewModel.saveRecord()
            false -> binding.btnRecordSave.anchorSnackBar(R.string.tv_record_warning_save)
        }
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

        fun getIntent(context: Context): Intent = Intent(context, RecordActivity::class.java)
    }
}
