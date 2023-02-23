package com.recodream_aos.recordream.presentation.storagy.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.data.entity.local.StorageEmotionData
import com.recodream_aos.recordream.databinding.ItemListStoreMyemotionBinding
import com.recodream_aos.recordream.presentation.storagy.adapter.StorageEmotionAdapter.EmotionViewHolder.Companion.diffUtil
import com.recodream_aos.recordream.util.DiffUtilItemCallback

class StorageEmotionAdapter(private val emotionItemClick: (Int) -> Unit) :
    ListAdapter<StorageEmotionData, StorageEmotionAdapter.EmotionViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewHolder {
        val binding =
            ItemListStoreMyemotionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return EmotionViewHolder(binding, emotionItemClick)
    }

    override fun onBindViewHolder(holder: EmotionViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class EmotionViewHolder(
        private val binding: ItemListStoreMyemotionBinding,
        private val emotionItemClick: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: StorageEmotionData) {
            binding.clStoreEmotion.setOnClickListener {
                emotionItemClick(absoluteAdapterPosition)
            }
            if (data.isSelected) {
                binding.ivStoreListEmotion.setImageResource(data.selectedImage)
                binding.tvStoreEmotion.setTextColor(Color.WHITE)
            } else {
                binding.ivStoreListEmotion.setImageResource(data.unSelectedImage)
                binding.tvStoreEmotion.setTextColor(Color.GRAY)
            }
            binding.tvStoreEmotion.text = data.feelingText

        }

        companion object {
            val diffUtil = DiffUtilItemCallback<StorageEmotionData>(
                onItemsTheSame = { old, new -> old.isSelected == new.isSelected },
                onContentsTheSame = { old, new -> old == new }
            )
        }
    }
}