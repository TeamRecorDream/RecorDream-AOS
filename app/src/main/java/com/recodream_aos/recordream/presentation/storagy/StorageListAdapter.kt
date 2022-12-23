package com.recodream_aos.recordream.presentation.storagy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.data.remote.response.ResponseStoreCard
import com.recodream_aos.recordream.databinding.ItemListStoreListBinding

//private val itemClick: ((ResponseStoreCard) -> (Unit))
class StorageListAdapter() :
    RecyclerView.Adapter<StorageListAdapter.StorageViewHolder>() {
    private var listCard = emptyList<ResponseStoreCard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageViewHolder {
        val binding =
            ItemListStoreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StorageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StorageViewHolder, position: Int) {
        holder.onBind(listCard[position])
    }

    override fun getItemCount(): Int = listCard.size

    fun setRepoList(repoList: List<ResponseStoreCard>) {
        this.listCard = repoList.toList()
        notifyDataSetChanged()
    }

    class StorageViewHolder(private val binding: ItemListStoreListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseStoreCard) {
            binding.card = data
        }
    }
}

