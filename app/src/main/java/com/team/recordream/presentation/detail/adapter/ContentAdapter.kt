package com.team.recordream.presentation.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.presentation.detail.adapter.viewHolder.ContentViewHolder
import com.team.recordream.presentation.detail.model.Content

class ContentAdapter : RecyclerView.Adapter<ContentViewHolder>() {
    private val items: MutableList<Content> =
        mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder =
        ContentViewHolder.from(parent)

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(content: List<Content>) {
        items.clear()
        items.addAll(content)
        notifyDataSetChanged()
    }
}
