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
import com.recodream_aos.recordream.presentation.storagy.StorageViewModel
import com.recodream_aos.recordream.presentation.storagy.adapter.StorageListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoragyListFragment : Fragment() {
    private var _binding: FragmentStoragyListBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private lateinit var storageListAdapter: StorageListAdapter
    private val storageViewModel by viewModels<StorageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoragyListBinding.inflate(layoutInflater, container, false)
        initAdapter()
        binding.viewModel = storageViewModel
        observer()
        return binding.root
    }

    private fun observer() {
        storageViewModel.storageRecords.observe(viewLifecycleOwner) {
            storageListAdapter.submitList(it)
        }
        storageViewModel.recordIsEmpty.observe(viewLifecycleOwner) {
            if (!it) binding.tvStorageNoRecord.visibility = View.VISIBLE
        }
    }

    private fun initAdapter() {
        storageListAdapter = StorageListAdapter {
            val intent = Intent(requireContext(), DocumentActivity::class.java)
        }
        binding.rvStorageList.adapter = storageListAdapter
        initItemDecoration()
    }

    private fun initItemDecoration() {
        binding.rvStorageList.addItemDecoration(
            MyDecoration(resources.getDimensionPixelOffset(R.dimen.margin_15), 1)
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
