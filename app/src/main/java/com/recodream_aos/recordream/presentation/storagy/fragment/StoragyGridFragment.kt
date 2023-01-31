package com.recodream_aos.recordream.presentation.storagy.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.FragmentStoragyGridBinding
import com.recodream_aos.recordream.presentation.document.DocumentActivity
import com.recodream_aos.recordream.presentation.storagy.MyDecoration
import com.recodream_aos.recordream.presentation.storagy.adapter.StorageGridAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        storageGridAdapter =
            StorageGridAdapter {
                val intent = Intent(requireContext(), DocumentActivity::class.java)
            }
        binding.rvStoreGrid.adapter = storageGridAdapter
        initItemDecoration()
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
