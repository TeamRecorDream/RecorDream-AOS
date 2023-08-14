package com.team.recordream.presentation.document2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.presentation.document2.adapter.viewHolder.ContentViewHolder
import com.team.recordream.presentation.document2.model.ContentUiModel

//class ContentAdapter : ListAdapter<ContentUiModel, ContentViewHolder>(diffCallBack) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
//        return ContentViewHolder(
//            ContentViewHolder.getView(
//                parent,
//                LayoutInflater.from(parent.context),
//            ),
//        )
//    }
//
//    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//    companion object {
//        private val diffCallBack = object : DiffUtil.ItemCallback<ContentUiModel>() {
//            override fun areItemsTheSame(
//                oldItem: ContentUiModel,
//                newItem: ContentUiModel,
//            ): Boolean {
//                return oldItem.title == newItem.title
//            }
//
//            override fun areContentsTheSame(
//                oldItem: ContentUiModel,
//                newItem: ContentUiModel,
//            ): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//}

class ContentAdapter : RecyclerView.Adapter<ContentViewHolder>() {
    val list = listOf<ContentUiModel>(
        ContentUiModel("나의 꿈기록", "123123"),
        ContentUiModel("노트", "123123"),
    )

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            ContentViewHolder.getView(
                parent,
                LayoutInflater.from(parent.context),
            ),
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(list[position])
    }
}
