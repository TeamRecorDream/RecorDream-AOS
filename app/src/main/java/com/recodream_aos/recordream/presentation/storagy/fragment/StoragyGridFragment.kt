package com.recodream_aos.recordream.presentation.storagy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.recodream_aos.recordream.databinding.FragmentStoragyGridBinding
import com.recodream_aos.recordream.presentation.storagy.adapter.StorageGridAdapter

class StoragyGridFragment : Fragment() {
    private var _binding: FragmentStoragyGridBinding? = null
    private val binding get() = _binding ?: error("Binding이 연결되지 않았습니다.")
    private lateinit var storageGridAdapter: StorageGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoragyGridBinding.inflate(layoutInflater, container, false)
        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        storageGridAdapter = StorageGridAdapter()
        binding.rvStoreGrid.adapter = storageGridAdapter
        storageGridAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
