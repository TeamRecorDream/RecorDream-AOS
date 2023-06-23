package com.recodream_aos.recordream.presentation.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivitySearchBinding
import com.recodream_aos.recordream.presentation.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : BindingActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private val searchViewModel: SearchViewModel by viewModels()
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        attachAdapter()
        setClickEvent()
        observeSearchResult()
    }

    override fun onStart() {
        super.onStart()
        // 상세보기뷰에서 인텐트 넘겨줌
        // adapter.updateRemovedItem()
        // notifyItemChanged
    }

    private fun initViewModel() {
        binding.viewModel = searchViewModel
        binding.lifecycleOwner = this
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
