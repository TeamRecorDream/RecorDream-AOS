package com.team.recordream.presentation.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.presentation.detail.adapter.viewHolder.GenreTagViewHolder
import com.team.recordream.presentation.record.uistate.Genre

class GenreTagAdapter : RecyclerView.Adapter<GenreTagViewHolder>() {
    private val items: MutableList<Genre> = mutableListOf(Genre.NOTHING)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreTagViewHolder =
        GenreTagViewHolder.from(parent)

    override fun onBindViewHolder(holder: GenreTagViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(genre: List<Genre>) {
        items.clear()
        when (genre.isEmpty()) {
            true -> items.add(Genre.NOTHING)
            false -> items.addAll(genre)
        }

        notifyDataSetChanged()
    }
}
