package com.recodream_aos.recordream.presentation.storagy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.data.entity.remote.response.ResponseStorage
import com.recodream_aos.recordream.databinding.ItemListStoreGridBinding
import com.recodream_aos.recordream.databinding.ItemListStoreTagBinding
import com.recodream_aos.recordream.presentation.storagy.adapter.StorageGridAdapter.StorageGridViewHolder.Companion.diffUtil

import com.recodream_aos.recordream.util.DiffUtilItemCallback

class StorageGridAdapter(private val itemClick: (ResponseStorage.Record) -> Unit) :
    ListAdapter<ResponseStorage.Record, StorageGridAdapter.StorageGridViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageGridViewHolder {
        val binding =
            ItemListStoreGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StorageGridViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: StorageGridViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class StorageGridViewHolder(
        private val binding: ItemListStoreGridBinding,
        private val itemClick: (ResponseStorage.Record) -> (Unit)
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseStorage.Record) {
            binding.root.setOnClickListener {
                itemClick(data)
            }
            var dreamDescription = data.date
            dreamDescription = dreamDescription.replace(" ".toRegex(), "\u00A0")
            binding.tvStoreGridDay.text = dreamDescription
            binding.tvStoreGridDescription.text = data.title
            binding.gridContainerDreamTag.run {
                val bindingDreamTag = {
                    ItemListStoreTagBinding.inflate(LayoutInflater.from(binding.root.context))
                }

                data.genre.map { item ->
                    bindingDreamTag().apply {
                        tvStoreTag.text = item.toString()
                    }
                }.forEach {
                    addView(it.root)
                }
            }
        }

        companion object {
            val diffUtil = DiffUtilItemCallback<ResponseStorage.Record>(
                onItemsTheSame = { old, new -> old.id == new.id },
                onContentsTheSame = { old, new -> old == new }
            )
        }
    }

}