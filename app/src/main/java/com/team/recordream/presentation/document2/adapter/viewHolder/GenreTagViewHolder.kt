package com.team.recordream.presentation.document2.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.databinding.ItemDocumentTagBinding
import com.team.recordream.presentation.record.uistate.Genre

class GenreTagViewHolder(
    private val binding: ItemDocumentTagBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: Genre) {
        binding.genre = genre
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemDocumentTagBinding =
            ItemDocumentTagBinding.inflate(layoutInflater, parent, false)
    }
}
