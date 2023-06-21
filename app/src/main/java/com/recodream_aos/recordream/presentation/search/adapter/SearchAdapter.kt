package com.recodream_aos.recordream.presentation.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.domain.model.SearchedRecord

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {
    private val results = mutableListOf<SearchedRecord>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            SearchViewHolder.getView(
                parent,
                LayoutInflater.from(parent.context),
            ),
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int = results.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateSearchResult(newResults: List<SearchedRecord>) {
        results.clear()
        results.addAll(newResults)
        notifyDataSetChanged()
    }
}
