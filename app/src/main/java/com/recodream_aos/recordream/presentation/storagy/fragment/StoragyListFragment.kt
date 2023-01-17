package com.recodream_aos.recordream.presentation.storagy.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.FragmentStoragyListBinding
import com.recodream_aos.recordream.presentation.document.DocumentActivity
import com.recodream_aos.recordream.presentation.storagy.MyDecoration
import com.recodream_aos.recordream.presentation.storagy.adapter.StorageListAdapter
import com.recodream_aos.recordream.presentation.storagy.StorageListViewModel

class StoragyListFragment : Fragment() {
    private var _binding: FragmentStoragyListBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private lateinit var storageListAdapter: StorageListAdapter
    private val viewModel by viewModels<StorageListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoragyListBinding.inflate(layoutInflater, container, false)
        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        storageListAdapter = StorageListAdapter {
            val intent = Intent(requireContext(), DocumentActivity::class.java)
        }
        binding.rvStorageList.adapter = storageListAdapter
        storageListAdapter.submitList(viewModel.)
        initItemDecoration()
    }

    private fun initItemDecoration() {
        binding.rvStorageList.addItemDecoration(
            MyDecoration(resources.getDimensionPixelOffset(R.dimen.margin_15), 1)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}