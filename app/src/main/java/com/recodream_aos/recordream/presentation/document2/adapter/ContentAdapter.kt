package com.recodream_aos.recordream.presentation.document2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.recodream_aos.recordream.presentation.document2.adapter.viewHolder.ContentViewHolder
import com.recodream_aos.recordream.presentation.document2.model.ContentUiModel

class ContentAdapter : ListAdapter<ContentUiModel, ContentViewHolder>(diffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            ContentViewHolder.getView(
                parent,
                LayoutInflater.from(parent.context),
            ),
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<ContentUiModel>() {
            override fun areItemsTheSame(
                oldItem: ContentUiModel,
                newItem: ContentUiModel,
            ): Boolean {
                return oldItem.category == newItem.category
            }

            override fun areContentsTheSame(
                oldItem: ContentUiModel,
                newItem: ContentUiModel,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
