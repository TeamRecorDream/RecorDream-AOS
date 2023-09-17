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
import com.team.recordream.presentation.detail.adapter.ContentAdapter
import com.team.recordream.presentation.detail.adapter.GenreTagAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : BindingActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val detailViewModel: DetailViewModel by viewModels()
    private val genreTagAdapter: GenreTagAdapter by lazy { GenreTagAdapter() }
    private val contentAdapter: ContentAdapter by lazy { ContentAdapter(this) }
    private val recordId: String by lazy {
        intent.getStringExtra(RECORD_ID) ?: throw IllegalArgumentException()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        collectGenre()
        collectIsRemoved()
        attachAdapter()
        setEventOnClick()
    }

    override fun onResume() {
        super.onResume()

        setupView()
    }

    private fun setupView() {
        detailViewModel.updateDetailRecord(recordId)
    }

    private fun setupBinding() {
        binding.vm = detailViewModel
        binding.lifecycleOwner = this
    }

    private fun collectGenre() {
        collectWithLifecycle(detailViewModel.tags) { genre ->
            genreTagAdapter.submitList(genre)
        }
    }

    private fun collectIsRemoved() {
        collectWithLifecycle(detailViewModel.isRemoved) { isRemoved ->
            if (isRemoved) finish()
        }
    }

    private fun attachAdapter() {
        binding.rvDocumentChip.adapter = genreTagAdapter
        binding.rvDocumentChip.setHasFixedSize(true)

        binding.vpDocumentContent.adapter = contentAdapter
        contentAdapter.fragments.addAll(
            listOf(
                DreamRecordFragment.from(detailViewModel),
                NoteFragment.from(detailViewModel)
            )
        )

        TabLayoutMediator(binding.tlDocument, binding.vpDocumentContent) { _, _ -> }.attach()
    }

    private fun setEventOnClick() {
        binding.ivDocumentMore.setOnClickListener { showMoreDialog() }
        binding.ivDocumentClose.setOnClickListener {
            finish()
        }
    }

    private fun showMoreDialog() {
        val documentBottomSheetFragment = DocumentBottomSheetFragment.from(detailViewModel)
        documentBottomSheetFragment.show(supportFragmentManager, documentBottomSheetFragment.tag)
    }

    private inline fun <T> collectWithLifecycle(
        flow: Flow<T>,
        crossinline action: (T) -> Unit
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

        fun getIntent(context: Context, id: String): Intent =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(RECORD_ID, id)
            }
    }
}
