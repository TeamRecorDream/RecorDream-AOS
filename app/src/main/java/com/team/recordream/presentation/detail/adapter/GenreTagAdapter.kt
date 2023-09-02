package com.team.recordream.presentation.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.presentation.detail.adapter.viewHolder.GenreTagViewHolder
import com.team.recordream.presentation.record.uistate.Genre

class GenreTagAdapter : RecyclerView.Adapter<GenreTagViewHolder>() {
    private val items: MutableList<Genre> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreTagViewHolder =
        GenreTagViewHolder.from(parent)

    override fun onBindViewHolder(holder: GenreTagViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(genre: List<Genre>) {
        items.clear()
        items.addAll(genre)
        notifyDataSetChanged()
    }
}
