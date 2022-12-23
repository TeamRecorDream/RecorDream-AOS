package com.recodream_aos.recordream.presentation.storagy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.data.remote.response.ResponseStoreCard
import com.recodream_aos.recordream.databinding.ItemListStoreListBinding
import com.recodream_aos.recordream.util.DiffUtilItemCallback

class StorageListAdapter(private val itemClick: (ResponseStoreCard) -> (Unit)) :
    ListAdapter<ResponseStoreCard, StorageListAdapter.StorageViewHolder>(diffUtil) {
//    private var listCard = emptyList<ResponseStoreCard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageViewHolder {
        val binding =
            ItemListStoreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StorageViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: StorageViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class StorageViewHolder(
        private val binding: ItemListStoreListBinding,
        private val itemClick: (ResponseStoreCard) -> (Unit)
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseStoreCard) {
            binding.card = data
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }
    }

    companion object {
        val diffUtil = DiffUtilItemCallback<ResponseStoreCard>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}

