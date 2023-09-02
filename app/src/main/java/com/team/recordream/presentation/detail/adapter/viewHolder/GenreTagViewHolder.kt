package com.team.recordream.presentation.detail.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.databinding.ItemDetailTagBinding
import com.team.recordream.presentation.record.uistate.Genre

class GenreTagViewHolder(
    private val binding: ItemDetailTagBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: Genre) {
        binding.genre = genre
    }

    companion object {
        fun from(parent: ViewGroup): GenreTagViewHolder =
            GenreTagViewHolder(
                ItemDetailTagBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            )
    }
}
