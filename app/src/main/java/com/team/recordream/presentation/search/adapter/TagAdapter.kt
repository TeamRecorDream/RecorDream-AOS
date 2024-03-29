package com.team.recordream.presentation.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TagAdapter : RecyclerView.Adapter<TagViewHolder>() {
    private val genreTags = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(
            TagViewHolder.getView(
                parent,
                LayoutInflater.from(parent.context),
            ),
        )
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(genreTags[position])
    }

    override fun getItemCount(): Int = genreTags.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateTags(newGenreTags: List<Int>) {
        genreTags.clear()

        when (newGenreTags.isEmpty()) {
            true -> genreTags.add(NOTHING)
            false -> genreTags.addAll(newGenreTags)
        }
        notifyDataSetChanged()
    }

    companion object {
        private const val NOTHING = -1
    }
}
