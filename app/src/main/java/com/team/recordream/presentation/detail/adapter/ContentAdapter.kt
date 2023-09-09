package com.team.recordream.presentation.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.presentation.common.model.PlayButtonState
import com.team.recordream.presentation.detail.adapter.viewHolder.ContentViewHolder
import com.team.recordream.presentation.detail.model.Content

class ContentAdapter(
    private val onClick: (PlayButtonState) -> Unit,
) : RecyclerView.Adapter<ContentViewHolder>() {
    private val items: MutableList<Content> =
        mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder =
        ContentViewHolder.from(parent, onClick)

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
