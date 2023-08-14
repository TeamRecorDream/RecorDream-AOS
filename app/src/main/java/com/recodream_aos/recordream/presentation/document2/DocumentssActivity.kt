package com.recodream_aos.recordream.presentation.document2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityDocumentssBinding
import com.recodream_aos.recordream.presentation.document2.adapter.ContentAdapter
import com.recodream_aos.recordream.presentation.document2.adapter.GenreTagAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DocumentssActivity :
    BindingActivity<ActivityDocumentssBinding>(R.layout.activity_documentss) {
    private val contentAdapter: ContentAdapter by lazy { ContentAdapter() }
    private val genreTagAdapter: GenreTagAdapter by lazy { GenreTagAdapter() }
    private val documentViewModel: DocumentssViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViewModel()
        collectViewTags()
        attachAdapter()
    }

    private fun bindViewModel() {
        binding.viewModel = documentViewModel
        binding.lifecycleOwner = this
    }

    private fun collectViewTags() {
        collectWithLifecycle(documentViewModel.tags) {
            genreTagAdapter.submitList(it)
        }
    }

    private fun attachAdapter() {
        binding.rvDocumentChip.adapter = genreTagAdapter
        binding.vpDocumentContent.adapter = contentAdapter
        TabLayoutMediator(binding.tlDocument, binding.vpDocumentContent) { _, _ -> }.attach()
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
}
