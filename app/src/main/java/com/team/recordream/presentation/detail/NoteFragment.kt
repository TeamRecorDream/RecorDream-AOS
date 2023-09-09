package com.team.recordream.presentation.detail

import android.os.Bundle
import android.view.View
import com.team.recordream.R
import com.team.recordream.databinding.FragmentNoteBinding
import com.team.recordream.presentation.common.BindingFragment

class NoteFragment private constructor(
    private val detailViewModel: DetailViewModel,
) : BindingFragment<FragmentNoteBinding>(R.layout.fragment_note) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        binding.vm = detailViewModel
        binding.lifecycleOwner = this
    }

    companion object {
        fun from(documentViewModel: DetailViewModel): NoteFragment =
            NoteFragment(documentViewModel)
    }
}
