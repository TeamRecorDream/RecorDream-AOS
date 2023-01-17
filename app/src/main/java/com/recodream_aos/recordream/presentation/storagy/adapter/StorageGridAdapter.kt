package com.recodream_aos.recordream.presentation.storagy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.data.remote.response.storage.StoreCard
import com.recodream_aos.recordream.databinding.ItemListStoreGridBinding

class StorageGridAdapter() : RecyclerView.Adapter<StorageGridAdapter.StorageGridViewHolder>() {
    private var gridCard = emptyList<StoreCard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageGridViewHolder {
        val binding =
            ItemListStoreGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StorageGridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StorageGridViewHolder, position: Int) {
        holder.onBind(gridCard[position])
    }

    override fun getItemCount(): Int = gridCard.size

    class StorageGridViewHolder(private val binding: ItemListStoreGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: StoreCard) {
//            binding.card = data
        }
    }

}