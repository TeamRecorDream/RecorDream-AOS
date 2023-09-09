package com.team.recordream.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.team.recordream.R
import com.team.recordream.databinding.FragmentDreamRecordBinding
import com.team.recordream.presentation.common.BindingFragment
import com.team.recordream.util.recorder.Recorder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DreamRecordFragment private constructor(
    private val detailViewModel: DetailViewModel,
) : BindingFragment<FragmentDreamRecordBinding>(R.layout.fragment_dream_record) {
    private val recorder: Recorder by lazy { Recorder(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        collectRecorderState()
        collectRecordState()
        collectProgressRate()
    }

    private fun setupBinding() {
        binding.vm = detailViewModel
        binding.onClick = { detailViewModel.updateRecorderState() }
        binding.lifecycleOwner = this
    }

    private fun collectRecorderState() {
        collectWithLifecycle(detailViewModel.isPlayed) { state ->
            when (state) {
                true -> recorder.startPlaying(detailViewModel.recordingFilePath.value)
                false -> recorder.stopPlaying()
            }
        }
    }

    private fun collectRecordState() {
        collectWithLifecycle(detailViewModel.recordingFilePath) { filaPath ->
            when (filaPath) {
                null -> return@collectWithLifecycle
                else -> initView(filaPath)
            }
        }
    }

    private fun collectProgressRate() {
        collectWithLifecycle(detailViewModel.progressRate) {
            binding.pbDreamRecordBar.progress = it
        }
    }

    private fun initView(filaPath: String) {
        val recordingTime = recorder.getDuration(filaPath)

        detailViewModel.updateRecordingTime(recordingTime)
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

        fun from(detailViewModel: DetailViewModel): DreamRecordFragment =
            DreamRecordFragment(detailViewModel)
    }
}
