package com.recodream_aos.recordream.presentation.home // ktlint-disable filename

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.data.entity.remote.response.ResponseHome
import com.recodream_aos.recordream.databinding.HomeCardItemBinding
import com.recodream_aos.recordream.presentation.document.DocumentActivity
import com.recodream_aos.recordream.util.RecordreamMapping

class HomeViewPagerAdapter(private val itemClick: (String) -> Unit) :
    RecyclerView.Adapter<HomeViewPagerAdapter.PagerViewHolder>() {
    private val asyncDiffer = AsyncListDiffer(this, diffResult)

    private var homeCardList = mutableListOf<ResponseHome>()

    fun updateList(list: MutableList<ResponseHome.Record>) {
//        Log.i("list.size", homeCardList.size.toString())
//        homeCardList = list
//        this.notifyDataSetChanged()
        asyncDiffer.submitList(list)
    }

    class PagerViewHolder(
        private val binding: HomeCardItemBinding,
        private val itemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseHome.Record) {
            binding.tvHomeDate.text = data.date
            binding.tvHomeCardTitle.text = data.title
            applyData(data)

            binding.root.setOnClickListener {
                itemClick(data.id)
            }
        }

        val recorDreamMapping = RecordreamMapping()
        private fun applyData(response: ResponseHome.Record) {
            val applyEmotion = response.let { recorDreamMapping.matchHomeEmotion(it.emotion) }
            val applyGenre = response.let { recorDreamMapping.genreMapping(it.genre) }
            val applyCardImage =
                response.let { recorDreamMapping.matchHomeColor(it.dream_color) }

            binding.clHomeCard.setBackgroundResource(applyCardImage)
            binding.ivHomeEmoticon.setBackgroundResource(applyEmotion)
            binding.tvHomeCardTitle.text = response.title

            binding.tvHomeDate.text = response.date
            if (response.genre.size == 1) {
                binding.tvHomeGenre1.text = "#${applyGenre[0]}"
            }
            if (response.genre.size == 2) {
                binding.tvHomeGenre1.text = "#${applyGenre[0]}"
                binding.tvHomeGenre2.text = "#${applyGenre[1]}"
            }
            if (response.genre.size == 3) {
                binding.tvHomeGenre1.text = "#${applyGenre[0]}"
                binding.tvHomeGenre2.text = "#${applyGenre[1]}"
                binding.tvHomeGenre3.text = "#${applyGenre[2]}"
            }
            when (response.genre.size) {
                1 -> {
                    binding.tvHomeGenre1.visibility = View.VISIBLE
                    binding.tvHomeGenre2.visibility = View.INVISIBLE
                    binding.tvHomeGenre3.visibility = View.INVISIBLE
                }
                2 -> {
                    binding.tvHomeGenre1.visibility = View.VISIBLE
                    binding.tvHomeGenre2.visibility = View.VISIBLE
                    binding.tvHomeGenre3.visibility = View.INVISIBLE
                }
                3 -> {
                    binding.tvHomeGenre1.visibility = View.VISIBLE
                    binding.tvHomeGenre2.visibility = View.VISIBLE
                    binding.tvHomeGenre3.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding: HomeCardItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.home_card_item,
            parent,
            false
        )
        return PagerViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBind(asyncDiffer.currentList[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, DocumentActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return asyncDiffer.currentList.size
    }

    companion object {
        private val diffResult = object : DiffUtil.ItemCallback<ResponseHome.Record>() {
            override fun areItemsTheSame(
                oldItem: ResponseHome.Record,
                newItem: ResponseHome.Record
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ResponseHome.Record,
                newItem: ResponseHome.Record
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
