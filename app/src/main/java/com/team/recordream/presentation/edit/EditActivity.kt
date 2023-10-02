package com.team.recordream.presentation.edit

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
import com.team.recordream.databinding.ActivityEditBinding
import com.team.recordream.presentation.common.BindingActivity
import com.team.recordream.presentation.detail.DetailActivity
import com.team.recordream.presentation.record.adapter.RecordAdapter
import com.team.recordream.presentation.record.model.EmotionState
import com.team.recordream.util.StateHandler
import com.team.recordream.util.anchorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditActivity : BindingActivity<ActivityEditBinding>(R.layout.activity_edit) {
    private val editViewModel: EditViewModel by viewModels()
    private val recordId by lazy {
        intent.getStringExtra(RECORD_ID) ?: throw IllegalArgumentException()
    }
    private val recordAdapter: RecordAdapter by lazy { RecordAdapter(editViewModel::updateSelectedEmotionId) }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        collectViewState()
        bindViewModel()
        setEditView()
        attachAdapter()
        setClickListener()
    }

    private fun collectViewState() {
        collectWithLifecycle(editViewModel.emotion) { emotion ->
            val emotionStateContainer =
                EmotionState.getEmotionContainer(emotion ?: EmotionState.SELECTED_ANYTHING)

            recordAdapter.submitList(emotionStateContainer)
        }

        collectWithLifecycle(editViewModel.title) { title ->
            editViewModel.updateSaveButtonEnabled(title)
        }

        collectWithLifecycle(editViewModel.stateHandlerOfSavingRecord) { result ->
            when (result) {
                is StateHandler.VALID -> navigateToDetailView(result.recordId)
                is StateHandler.INVALID -> Log.e("RecordActivity", "에러 핸들링 필요")
                is StateHandler.DISCONNECT -> Log.e("RecordActivity", "에러 핸들링 필요")
                is StateHandler.IDLE -> Log.e("RecordActivity", "DEFAULT")
            }
        }
    }

    private fun navigateToDetailView(recordId: String) {
        startActivity(DetailActivity.getIntent(this, recordId))
        finish()
    }

    private fun setClickListener() {
        binding.clEditDate.setOnClickListener {
            Log.d("123123", "123123")
            initDatePickerDialog()
        }
        binding.ivRecordClose.setOnClickListener { finish() }
        binding.btnRecordSave.setOnClickListener { editRecord() }
        binding.clRecordRecordBtn.setOnClickListener { showWarningOfRecording() }
    }

    private fun initDatePickerDialog() {
        val cal = Calendar.getInstance()

        DatePickerDialog(
            this,
            editViewModel.updateDate(),
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH),
        ).apply {
            datePicker.maxDate = System.currentTimeMillis()
            show()
        }
    }

    private fun bindViewModel() {
        binding.viewModel = editViewModel
        binding.lifecycleOwner = this
    }

    private fun setEditView() {
        editViewModel.initEditViewState(recordId)
    }

    private fun attachAdapter() {
        binding.rvRecordEmotion.adapter = recordAdapter
        binding.rvRecordEmotion.setHasFixedSize(true)
    }

    private fun editRecord() {
        when (editViewModel.isSaveEnabled.value) {
            true -> {
                editViewModel.editRecord(recordId)
                finish()
            }

            false -> binding.btnRecordSave.anchorSnackBar(R.string.tv_record_warning_save)
        }
    }


    private fun showWarningOfRecording() {
        val content = when (editViewModel.voiceId.value != null) {
            true -> R.string.tv_record_warning_editable_recording
            false -> R.string.tv_record_warning_disable_recording
        }

        binding.btnRecordSave.anchorSnackBar(content)
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
        private const val RECORD_ID = "RECORD_ID"

        fun getIntent(context: Context, recordId: String): Intent =
            Intent(context, EditActivity::class.java).apply {
                putExtra(RECORD_ID, recordId)
            }
    }
}