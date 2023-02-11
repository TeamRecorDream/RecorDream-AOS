package com.recodream_aos.recordream.presentation.storagy.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.data.entity.remote.response.ResponseStorage
import com.recodream_aos.recordream.databinding.FragmentStoragyGridBinding
import com.recodream_aos.recordream.presentation.document.DocumentActivity
import com.recodream_aos.recordream.presentation.storagy.MyDecoration
import com.recodream_aos.recordream.presentation.storagy.StorageViewModel
import com.recodream_aos.recordream.presentation.storagy.adapter.StorageGridAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoragyGridFragment : Fragment() {
    private var _binding: FragmentStoragyGridBinding? = null
    private val binding get() = _binding ?: error("Binding이 연결되지 않았습니다.")
    private lateinit var storageGridAdapter: StorageGridAdapter
    private val storageViewModel by viewModels<StorageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoragyGridBinding.inflate(layoutInflater, container, false)
        initAdapter()
        binding.viewModel = storageViewModel
        observer()
        return binding.root
    }

    private fun observer() {
        storageViewModel.storageRecords.observe(viewLifecycleOwner) {
            Log.d("Storagy", "observerTrue: ${storageViewModel.storageRecords.value}")
            binding.tvStorageNoRecord.visibility = View.INVISIBLE
            storageGridAdapter.submitList(it)
        }
        storageViewModel.recordIsEmpty.observe(viewLifecycleOwner) {
//            if (!it) binding.tvStorageNoRecord.visibility = View.VISIBLE
            if(it == false){
                binding.tvStorageNoRecord.visibility = View.VISIBLE
                Log.d("Storagy", "observerFalse: $it")
            }
            else{
                initAdapter()
            }
        }
    }

    private fun initAdapter() {
        storageGridAdapter =
            StorageGridAdapter {
                val intent = Intent(requireContext(), DocumentActivity::class.java)
            }
        binding.rvStoreGrid.adapter = storageGridAdapter
        initItemDecoration()

        val genre1 = listOf<Int>(1, 2)
        val genre2 = listOf<Int>(1, 2, 3)
        val genre3 = listOf<Int>(1, 2, 4)

        val recordList = listOf<ResponseStorage.Record>(
//            ResponseStorage.Record("13", "123", 1, genre1, "134"),
//            ResponseStorage.Record("3", "124", 1, genre1, "1253"),
//            ResponseStorage.Record("15", "125", 1, genre1, "1223423"),
        )
//        storageGridAdapter.submitList(recordList)
    }

    private fun initItemDecoration() {
        binding.rvStoreGrid.addItemDecoration(
            MyDecoration(resources.getDimensionPixelOffset(R.dimen.margin_15), 1)
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
