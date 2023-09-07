package com.team.recordream.presentation.detail

import com.team.recordream.R
import com.team.recordream.databinding.FragmentNoteBinding
import com.team.recordream.presentation.common.BindingFragment

class NoteFragment private constructor(
    private val detailViewModel: DetailViewModel,
) : BindingFragment<FragmentNoteBinding>(R.layout.fragment_note) {

    companion object {
        fun from(documentViewModel: DetailViewModel): NoteFragment =
            NoteFragment(documentViewModel)
    }
}
