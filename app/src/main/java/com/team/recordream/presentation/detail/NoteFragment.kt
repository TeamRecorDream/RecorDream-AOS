package com.team.recordream.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.team.recordream.R
import com.team.recordream.databinding.FragmentNoteBinding
import com.team.recordream.presentation.common.BindingFragment

class NoteFragment : BindingFragment<FragmentNoteBinding>(R.layout.fragment_note) {
    private val detailViewModel: DetailViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        binding.vm = detailViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}
