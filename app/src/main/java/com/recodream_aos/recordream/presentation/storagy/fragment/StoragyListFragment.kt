package com.recodream_aos.recordream.presentation.storagy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recodream_aos.recordream.databinding.FragmentStoragyListBinding
import com.recodream_aos.recordream.presentation.storagy.StorageListAdapter
import com.recodream_aos.recordream.presentation.storagy.StorageListViewModel

class StoragyListFragment : Fragment() {
    private var _binding: FragmentStoragyListBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private lateinit var storageListAdapter: StorageListAdapter
    private val viewModel by viewModels<StorageListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoragyListBinding.inflate(layoutInflater, container, false)
        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        storageListAdapter = StorageListAdapter()
        binding.rvStorageList.adapter = storageListAdapter
        storageListAdapter.setRepoList(viewModel.mockHomelist)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}