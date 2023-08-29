package com.team.recordream.presentation.record

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.team.recordream.R
import com.team.recordream.base.BindingActivity
import com.team.recordream.databinding.ActivityRecordBinding
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
    private val recordAdapter: RecordAdapter by lazy { RecordAdapter(::updateEmotionState) }
    private val viewMode by lazy { intent.getStringExtra(VIEW_MODE) }
    private val recordId by lazy {
        intent.getStringExtra(RECORD_ID) ?: throw IllegalArgumentException()
    }

    private fun updateEmotionState(emotionId: Int) {
        recordViewModel.updateSelectedEmotionId(emotionId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        collectViewState()
        initView()
        bindViewModel()
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
                is VALID -> navigateToDocumentView(result.recordId)
                is INVALID -> Log.e("RecordActivity", "에러 핸들링 필요")
                is DISCONNECT -> Log.e("RecordActivity", "에러 핸들링 필요")
                is IDLE -> Log.e("RecordActivity", "DEFAULT")
            }
        }
    }

    private fun navigateToDocumentView(recordId: String) {
        startActivity(DetailActivity.getIntent(this, recordId))
        finish()
    }

    private fun initView() {
        when (viewMode) {
            CREATE_MODE -> binding.tvRecordRecord.text = getString(R.string.tv_record_create)
            EDIT_MODE -> {
                binding.tvRecordRecord.text = getString(R.string.tv_record_edit)
                setEditView()
            }
        }
    }

    private fun setEditView() {
        recordViewModel.initEditViewState(recordId)
    }

    private fun bindViewModel() {
        binding.viewModel = recordViewModel
        binding.lifecycleOwner = this
    }

    private fun attachAdapter() {
        binding.rvRecordEmotion.adapter = recordAdapter
        binding.rvRecordEmotion.setHasFixedSize(true)
    }

    private fun setClickListener() {
        binding.clRecordDateBtn.setOnClickListener { initDatePickerDialog() }
        binding.ivRecordClose.setOnClickListener { finish() }
        binding.clRecordRecordBtn.setOnClickListener {
            when (viewMode) {
                EDIT_MODE -> binding.btnRecordSave.anchorSnackBar(R.string.tv_record_warning_disable_recording)
                CREATE_MODE -> initRecordBottomSheetDialog()
            }
        }
        binding.btnRecordSave.setOnClickListener {
            when (viewMode) {
                EDIT_MODE -> editRecord()
                CREATE_MODE -> createRecord()
            }
        }
    }

    private fun initDatePickerDialog() {
        // TODO: maxDate를 수정하기 날짜 이후도 선택 가능한지
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

    private fun editRecord() {
        recordViewModel.editRecord(recordId)
        finish()
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
        private const val VIEW_MODE = "VIEW_MODE"
        private const val RECORD_ID = "RECORD_ID"
        const val CREATE_MODE = "CREATE_MODE"
        const val EDIT_MODE = "EDIT_MODE"

        fun getIntent(context: Context, viewMode: String, recordId: String?): Intent =
            Intent(context, RecordActivity::class.java).apply {
                putExtra(VIEW_MODE, viewMode)
                putExtra(RECORD_ID, recordId)
            }

        @JvmStatic
        @BindingAdapter("formattedDate")
        fun formatDate(textView: TextView, date: String) {
            if (!date.contains("-")) {
                textView.text = date.substringBefore(" ").replace("/", "-")
                return
            }
            textView.text = date
        }
    }
}
