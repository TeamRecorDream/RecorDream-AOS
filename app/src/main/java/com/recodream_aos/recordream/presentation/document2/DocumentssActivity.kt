package com.recodream_aos.recordream.presentation.document2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

        binding.viewModel = documentViewModel
        binding.lifecycleOwner = this

        collectWithLifecycle(documentViewModel.tags) {
            genreTagAdapter.submitList(it)
        }

        binding.rvDocumentChip.adapter = genreTagAdapter
        binding.vpDocumentContent.adapter = contentAdapter
// 탭레이아웃
//        TabLayoutMediator(binding.tlDocument, binding.vpDocumentContent) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
//        }.attach()
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
