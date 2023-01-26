package com.recodream_aos.recordream.presentation.storagy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.data.entity.remote.response.ResponseStorage
import com.recodream_aos.recordream.databinding.ItemListStoreListBinding
import com.recodream_aos.recordream.util.DiffUtilItemCallback

class StorageListAdapter(private val itemClick: (ResponseStorage.Record) -> (Unit)) :
    ListAdapter<ResponseStorage.Record, StorageListAdapter.StorageViewHolder>(diffUtil) {

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
        private val itemClick: (ResponseStorage.Record) -> (Unit)
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseStorage.Record) {

            binding.root.setOnClickListener {
                itemClick(data)
            }
            when(data.emotion){

            }
            binding.ivStoreListIcon
        }
    }

    companion object {
        val diffUtil = DiffUtilItemCallback<ResponseStorage.Record>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}

