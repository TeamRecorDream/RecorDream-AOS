package com.team.recordream.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.team.recordream.presentation.detail.adapter.viewHolder.GenreTagViewHolder
import com.team.recordream.presentation.record.uistate.Genre

class GenreTagAdapter : ListAdapter<Genre, GenreTagViewHolder>(diffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreTagViewHolder {
        return GenreTagViewHolder(
            GenreTagViewHolder.getView(
                parent,
                LayoutInflater.from(parent.context),
            ),
        )
    }

    override fun onBindViewHolder(holder: GenreTagViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(
                oldItem: Genre,
                newItem: Genre,
            ): Boolean {
                return oldItem.genreId == newItem.genreId
            }

            override fun areContentsTheSame(
                oldItem: Genre,
                newItem: Genre,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}