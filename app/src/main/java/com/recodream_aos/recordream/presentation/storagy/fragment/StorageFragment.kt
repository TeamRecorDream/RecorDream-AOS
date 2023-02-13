package com.recodream_aos.recordream.presentation.storagy.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.recodream_aos.recordream.databinding.FragmentStorageBinding
import com.recodream_aos.recordream.presentation.document.DocumentActivity
import com.recodream_aos.recordream.presentation.storagy.StorageViewModel
import com.recodream_aos.recordream.presentation.storagy.adapter.StorageEmotionAdapter
import com.recodream_aos.recordream.presentation.storagy.adapter.StorageGridAdapter
import com.recodream_aos.recordream.presentation.storagy.adapter.StorageListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StorageFragment : Fragment() {
    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding ?: error("binding not init")

    private lateinit var storageGridAdapter: StorageGridAdapter
    private lateinit var storageListAdapter: StorageListAdapter
    private val storageViewModel by viewModels<StorageViewModel>()
    private val storageEmotionAdapter = StorageEmotionAdapter(::emotionClick)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStorageBinding.inflate(layoutInflater, container, false)
        emotionAdapterInit()
        initGridAdapter()
        binding.viewModel = storageViewModel
        observer()
        selectShowView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storageViewModel.initServer(0)
    }

    private fun observer() {
        storageViewModel.storageRecords.observe(viewLifecycleOwner) {
            Log.d("Storagy", "observerTrue: ${storageViewModel.storageRecords.value}")
            binding.tvStorageNoList.visibility = View.INVISIBLE
            storageGridAdapter.submitList(it)
            storageEmotionAdapter.submitList(storageViewModel.storageList)
            binding.rvStorage.visibility = View.VISIBLE
        }
        storageViewModel.recordIsEmpty.observe(viewLifecycleOwner) {
//            if (!it) binding.tvStorageNoRecord.visibility = View.VISIBLE
            if (it == false) {
                binding.tvStorageNoList.visibility = View.VISIBLE
                Log.d("Storagy", "observerFalse: $it")
            } else {
                initGridAdapter()
            }
        }
    }

    private fun emotionAdapterInit() {
        binding.rvStorageEmotion.adapter = storageEmotionAdapter
        storageEmotionAdapter.submitList(storageViewModel.storageList)
    }

    private fun initGridAdapter() {
        storageGridAdapter =
            StorageGridAdapter {
                val intent = Intent(requireContext(), DocumentActivity::class.java)
            }
        binding.rvStorage.adapter = storageGridAdapter
        binding.rvStorage.layoutManager = GridLayoutManager(context, 2)
    }

    private fun initListAdapter() {
        storageGridAdapter =
            StorageGridAdapter {
                val intent = Intent(requireContext(), DocumentActivity::class.java)
            }
        binding.rvStorage.adapter = storageGridAdapter
    }

    private fun selectShowView() {
        Log.d("StorageFragment", "selectShowView: ")
        binding.ivStorageSelectList.setOnClickListener {
            initListAdapter()
            binding.rvStorage.layoutManager = LinearLayoutManager(context)
            binding.ivStorageSelectGallery.isSelected = false
            binding.ivStorageSelectList.isSelected = true
        }
        binding.ivStorageSelectGallery.setOnClickListener {
            initGridAdapter()
            binding.rvStorage.layoutManager = GridLayoutManager(context, 2)
            binding.ivStorageSelectGallery.isSelected = true
            binding.ivStorageSelectList.isSelected = false
        }
    }

    private fun emotionClick(index: Int) {
        storageViewModel.initServer(index)
        Log.d("Storagefragment", "emotionClick: $index")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
