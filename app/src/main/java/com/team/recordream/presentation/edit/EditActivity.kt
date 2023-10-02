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
import com.team.recordream.R
import com.team.recordream.databinding.ActivityEditBinding
import com.team.recordream.presentation.common.BindingActivity
import com.team.recordream.presentation.record.adapter.RecordAdapter
import com.team.recordream.util.anchorSnackBar
import dagger.hilt.android.AndroidEntryPoint

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

        bindViewModel()
        setEditView()
        attachAdapter()
        setClickListener()
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

    companion object {
        private const val RECORD_ID = "RECORD_ID"

        fun getIntent(context: Context, recordId: String): Intent =
            Intent(context, EditActivity::class.java).apply {
                putExtra(RECORD_ID, recordId)
            }
    }
}