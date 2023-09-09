package com.team.recordream.presentation.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.team.recordream.R
import com.team.recordream.databinding.ActivitySearchBinding
import com.team.recordream.presentation.common.BindingActivity
import com.team.recordream.presentation.detail.DetailBottomSheetFragment
import com.team.recordream.presentation.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : BindingActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private val searchViewModel: SearchViewModel by viewModels()
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter(::navigateToDetailView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        attachAdapter()
        setClickEvent()
        observeSearchResult()
    }

    private fun setupBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = searchViewModel
    }

    private fun attachAdapter() {
        binding.rvSearchResult.adapter = searchAdapter
    }

    private fun setClickEvent() {
        binding.ivSearchBackBtn.setOnClickListener { finish() }
        binding.etSearchEnter.setOnEditorActionListener { _, actionId, _ ->
            actionId == EditorInfo.IME_ACTION_DONE && searchViewModel.postSearch().let { true }
        }
    }

    private fun observeSearchResult() {
        collectWithLifecycle(searchViewModel.searchResult) { searchResult ->
            searchAdapter.updateSearchResult(searchResult)
        }
    }

    private fun navigateToDetailView(id: String) {
        val detailBottomSheetFragment = DetailBottomSheetFragment.from(id)
        detailBottomSheetFragment.show(supportFragmentManager, detailBottomSheetFragment.tag)
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
        fun getIntent(context: Context): Intent =
            Intent(context, SearchActivity::class.java)
    }
}
