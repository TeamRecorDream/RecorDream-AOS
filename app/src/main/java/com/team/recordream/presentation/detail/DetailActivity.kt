package com.team.recordream.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import com.team.recordream.R
import com.team.recordream.databinding.ActivityDetailBinding
import com.team.recordream.presentation.common.BindingActivity
import com.team.recordream.presentation.common.model.PlayButtonState
import com.team.recordream.presentation.detail.adapter.ContentAdapter
import com.team.recordream.presentation.detail.adapter.GenreTagAdapter
import com.team.recordream.util.recorder.Recorder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity :
    BindingActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val documentViewModel: DetailViewModel by viewModels()
    private val contentAdapter: ContentAdapter by lazy { ContentAdapter(documentViewModel::updateRecorderState) }
    private val genreTagAdapter: GenreTagAdapter by lazy { GenreTagAdapter() }
    private val documentBottomSheetFragment: DocumentBottomSheetFragment by lazy { DocumentBottomSheetFragment() }
    private val recorder: Recorder by lazy { Recorder(this) }
    private val recordId by lazy {
        intent.getStringExtra(RECORD_ID) ?: throw IllegalArgumentException()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        collectState()
        attachAdapter()
        setEventOnClick()
    }

    private fun setupBinding() {
        binding.viewModel = documentViewModel
        binding.lifecycleOwner = this
    }

    private fun collectState() {
        collectWithLifecycle(documentViewModel.content) { contents ->
            contentAdapter.submitList(contents)
        }

        collectWithLifecycle(documentViewModel.tags) { genre ->
            genreTagAdapter.submitList(genre)
        }

        collectWithLifecycle(documentViewModel.isRemoved) { isRemoved ->
            if (isRemoved) finish()
        }

        collectWithLifecycle(documentViewModel.recorderState) { recorderState ->
            when (recorderState) {
                PlayButtonState.RECORDER_STOP -> handleRecorderPlayState()
                PlayButtonState.RECORDER_PLAY -> handleRecorderStopState()
            }
        }

        collectWithLifecycle(documentViewModel.progressRate) { progressRate ->
            // TODO: 아이템 뷰 -> 프래그먼트
            // TODO: 액티비티 -> 바텀시트 프래그먼트
            // TODO: 가끔 나의꿈기록 늦게 업데이트 됨. 프래그먼트로 교체하면 해결
        }
    }

    private fun handleRecorderStopState() {
        recorder.stopPlaying()
    }

    private fun handleRecorderPlayState() {
        val file = documentViewModel.recordingFilePath ?: throw IllegalArgumentException()

        recorder.apply {
            documentViewModel.updateRunningTime(getDuration(file))
            startPlaying(file)
        }
    }

    private fun attachAdapter() {
        binding.rvDocumentChip.adapter = genreTagAdapter
        binding.rvDocumentChip.setHasFixedSize(true)
        binding.vpDocumentContent.adapter = contentAdapter
        TabLayoutMediator(binding.tlDocument, binding.vpDocumentContent) { _, _ -> }.attach()
    }

    private fun setEventOnClick() {
        binding.ivDocumentMore.setOnClickListener { showBottomSheet() }
        binding.ivDocumentClose.setOnClickListener { finish() }
    }

    private fun showBottomSheet() {
        documentBottomSheetFragment.show(supportFragmentManager, documentBottomSheetFragment.tag)
    }

    private fun setupView() {
        documentViewModel.updateDetailRecord(recordId)
    }

    override fun onResume() {
        super.onResume()
        setupView()
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
            Intent(context, DetailActivity::class.java).apply {
                putExtra(RECORD_ID, recordId)
            }
    }
}
