package com.recodream_aos.recordream.presentation.storagy.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.data.entity.local.StorageEmotionData
import com.recodream_aos.recordream.databinding.ItemListStoreMyemotionBinding

class StorageEmotionAdapter : RecyclerView.Adapter<StorageEmotionAdapter.EmotionViewHolder>() {
    val emotionList = mutableListOf<StorageEmotionData>()
    var emotionQuery = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: EmotionViewHolder, position: Int) {
        holder.onBind(emotionList[position])
    }

    override fun getItemCount(): Int = emotionList.size

    class EmotionViewHolder(
        private val binding: ItemListStoreMyemotionBinding,
        private val emotionItemClick: (Int, View) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: StorageEmotionData) {
            binding.clStoreEmotion.setOnClickListener {
                emotionItemClick(
                    adapterPosition, it
                )
            }
            binding.ivStoreListEmotion.setImageResource(data.feeling)
            binding.tvStoreEmotion.text = data.feelingText
        }
    }
}