package com.team.recordream.presentation.detail

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
