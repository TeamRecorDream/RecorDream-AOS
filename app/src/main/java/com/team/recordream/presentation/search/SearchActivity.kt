package com.team.recordream.presentation.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.team.recordream.R
import com.team.recordream.databinding.ActivitySearchBinding
import com.team.recordream.presentation.common.BindingActivity
import com.team.recordream.presentation.detail.DetailActivity
import com.team.recordream.presentation.search.adapter.SearchAdapter
import com.team.recordream.util.UiState
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
        getResultCount()
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
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
        binding.etSearchEnter.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard(binding.etSearchEnter)
                    actionId == EditorInfo.IME_ACTION_DONE && searchViewModel.postSearch()
                        .let { true }
                    return true
                }
                return false
            }
        })
    }

    private fun hideKeyboard(view: EditText) {
        val imm =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun observeSearchResult() {
        collectWithLifecycle(searchViewModel.searchResult) { searchResult ->
            searchAdapter.updateSearchResult(searchResult)
        }
    }

    private fun getResultCount() {
        lifecycleScope.launchWhenStarted {
            searchViewModel.resultCount.collectLatest { state ->
                when (state) {
                    is UiState.Success -> {
                        binding.lvStorageLottieLoading.pauseAnimation()
                        binding.lvStorageLottieLoading.visibility = View.INVISIBLE
                        binding.clLoadingBackground.visibility = View.INVISIBLE
                        binding.tvSearchCountResult.text =
                            getString(R.string.tv_search_count_record, state.data)
                    }

                    is UiState.Failure -> {
                    }

                    is UiState.Loading -> {
                        binding.lvStorageLottieLoading.playAnimation()
                        binding.clLoadingBackground.visibility = View.VISIBLE
                        binding.lvStorageLottieLoading.visibility = View.VISIBLE
                    }

                    is UiState.Empty -> {
                        binding.lvStorageLottieLoading.pauseAnimation()
                        binding.lvStorageLottieLoading.visibility = View.INVISIBLE
                        binding.clLoadingBackground.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun navigateToDetailView(recordId: String) {
        startActivity(DetailActivity.getIntent(this, recordId))
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
