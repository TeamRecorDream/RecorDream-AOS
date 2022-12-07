package com.recodream_aos.recordream.presentation.storagy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.FragmentStoragyListBinding

class StoragyListFragment : Fragment() {
    private var _binding: FragmentStoragyListBinding? = null
    private val binding get() = _binding ?: error("binding이 연결되지 않았습니다.")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storagy_list, container, false)
    }
}
