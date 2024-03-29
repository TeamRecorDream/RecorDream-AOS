package com.team.recordream.presentation.storagy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.recordream.R
import com.team.recordream.databinding.FragmentStorageBinding
import com.team.recordream.presentation.detail.DetailActivity
import com.team.recordream.presentation.storagy.MyDecoration
import com.team.recordream.presentation.storagy.StorageViewModel
import com.team.recordream.presentation.storagy.adapter.StorageEmotionAdapter
import com.team.recordream.presentation.storagy.adapter.StorageGridAdapter
import com.team.recordream.presentation.storagy.adapter.StorageListAdapter
import com.team.recordream.util.UiState
import com.team.recordream.util.makeSnackBar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class StorageFragment : Fragment() {
    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding ?: error("binding not init")
    private var storageCheck = true
    private var emotionCheck = 0
    private lateinit var storageGridAdapter: StorageGridAdapter
    private lateinit var storageListAdapter: StorageListAdapter
    private val storageViewModel by viewModels<StorageViewModel>()
    private val storageEmotionAdapter = StorageEmotionAdapter(::emotionClick)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStorageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emotionClick(0)
        emotionAdapterInit()
        initGridAdapter()
        binding.viewModel = storageViewModel
        StorageDataObserver()
        checkNetworkError()
        selectShowView()
    }

    override fun onResume() {
        super.onResume()
        storageViewModel.initServer(emotionCheck)
    }

    private fun StorageDataObserver() {
        with(storageViewModel) {
            storageCheckList.observe(viewLifecycleOwner) { showView ->
                if (showView) {
                    storageCheck = true
                    initGridAdapter()
                    storageViewModel.initServer(emotionCheck)
                } else {
                    storageCheck = false
                    initListAdapter()
                    storageViewModel.initServer(emotionCheck)
                }
            }

            storageRecords.observe(viewLifecycleOwner) { records ->
                binding.tvStorageNoList.visibility = View.INVISIBLE
                when (records) {
                    is UiState.Success -> {
                        binding.lvStorageLottieLoading.pauseAnimation()
                        binding.lvStorageLottieLoading.visibility = View.INVISIBLE
                        binding.clLoadingBackground.visibility = View.INVISIBLE
                        storageGridAdapter?.submitList(records.data)
                        if (storageCheck) {
                            storageGridAdapter.submitList(records.data)
                            storageEmotionAdapter.submitList(storageViewModel.storageList)
                        } else {
                            storageEmotionAdapter.submitList(storageViewModel.storageList)
                            storageListAdapter.submitList(records.data)
                        }
                    }

                    is UiState.Failure -> {
                        view?.makeSnackBar(R.string.network_error)
                    }

                    is UiState.Loading -> {
                        binding.lvStorageLottieLoading.playAnimation()
                        binding.clLoadingBackground.visibility = View.VISIBLE
                        binding.lvStorageLottieLoading.visibility = View.VISIBLE
                    }

                    is UiState.Empty -> {}
                }
            }
            storageRecordCount.observe(viewLifecycleOwner) { count ->
                val recordAllCount = getString(R.string.store_records_count, count)
                binding.tvStorageRecordCount.text = recordAllCount
                if (count == 0) {
                    binding.tvStorageNoList.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun checkNetworkError() {
        storageViewModel.errorMessage.observe(viewLifecycleOwner) {
            Timber.tag("storage").d(" $it")
        }
    }

    private fun emotionAdapterInit() {
        binding.rvStorageEmotion.adapter = storageEmotionAdapter
        storageEmotionAdapter.submitList(storageViewModel.storageList)
        binding.rvStorageEmotion.addItemDecoration(MyDecoration(70, 1))
    }

    private fun initGridAdapter() {
        storageGridAdapter = StorageGridAdapter { navigateToDetailView(it.id) }
        binding.rvStorage.adapter = storageGridAdapter
        binding.rvStorage.layoutManager = GridLayoutManager(context, 2)
    }

    private fun navigateToDetailView(recordId: String) {
        startActivity(DetailActivity.getIntent(requireContext(), recordId))
    }

    private fun initListAdapter() {
        storageListAdapter = StorageListAdapter { navigateToDetailView(it.id) }
        binding.rvStorage.adapter = storageListAdapter
        binding.rvStorage.layoutManager = LinearLayoutManager(context)
    }

    private fun selectShowView() {
        with(binding) {
            ivStorageSelectGallery.isSelected = true
            ivStorageSelectGallery.setOnClickListener {
                storageViewModel.isCheckShow(true)
                storageCheck = true
                ivStorageSelectGallery.isSelected = true
                ivStorageSelectList.isSelected = false
            }
            ivStorageSelectList.setOnClickListener {
                storageViewModel.isCheckShow(false)
                initListAdapter()
                ivStorageSelectGallery.isSelected = false
                ivStorageSelectList.isSelected = true
            }
        }
    }

    private fun emotionClick(index: Int) {
        storageViewModel.initServer(index)
        emotionCheck = index
        for (i in 0..6) {
            storageViewModel.storageList[i].isSelected = false
        }
        storageViewModel.storageList[index].isSelected = true
        storageEmotionAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
