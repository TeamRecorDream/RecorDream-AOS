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
import com.team.recordream.base.BindingActivity
import com.team.recordream.databinding.ActivityDetailBinding
import com.team.recordream.presentation.detail.adapter.ContentAdapter
import com.team.recordream.presentation.detail.adapter.GenreTagAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity :
    BindingActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val contentAdapter: ContentAdapter by lazy { ContentAdapter() }
    private val genreTagAdapter: GenreTagAdapter by lazy { GenreTagAdapter() }
    private val documentViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViewModel()
        collectViewTags()
        attachAdapter()
        initBottomSheetFragment()
        // intent.getStringExtra(RECORD_ID)
        documentViewModel.updateDetailRecord("64d9f5b01e9a1d32e19e0f36")
    }

    private fun initBottomSheetFragment() {
        DetailBottomSheetFragment().show(
            supportFragmentManager,
            DetailBottomSheetFragment().tag,
        )
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

    companion object {
        private const val RECORD_ID = "RECORD_ID"

        fun getIntent(context: Context, recordId: String): Intent =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(RECORD_ID, recordId)
            }
    }
}
