package com.recodream_aos.recordream.presentation.storagy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.recodream_aos.recordream.R
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
            var dreamDescription = data.title
            dreamDescription = dreamDescription.replace(" ".toRegex(), "\u00A0")

            binding.clStoreGridBackground.setBackgroundResource(checkEmotionBackground(data.emotion))
            binding.ivStoreGridIcon.setImageResource(checkemotionIcon(data.emotion))
            binding.tvStoreGridDay.text = data.date
            binding.tvStoreGridDescription.text = dreamDescription
            binding.gridContainerDreamTag.run {
                val bindingDreamTag = {
                    ItemListStoreTagBinding.inflate(LayoutInflater.from(binding.root.context))
                }
                removeAllViews()
                data.genre.map { item ->
                    bindingDreamTag().apply {
                        tvStoreTag.text = checkGenreList(item)
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

        fun checkemotionIcon(color: Int) = when (color) {
            1 -> R.drawable.feeling_m_joy
            2 -> R.drawable.feeling_m_sad
            3 -> R.drawable.feeling_m_scary
            4 -> R.drawable.feeling_m_strange
            5 -> R.drawable.feeling_m_shy
            else -> R.drawable.feeling_m_blank
        }

        fun checkEmotionBackground(color: Int) = when (color) {
            1 -> R.drawable.card_s_yellow
            2 -> R.drawable.card_s_blue
            3 -> R.drawable.card_s_red
            4 -> R.drawable.card_s_purple
            5 -> R.drawable.card_s_pink
            else -> R.drawable.card_s_white
        }

        fun checkGenreList(genre: Int) = when (genre) {
            0 -> "코미디"
            1 -> "로맨스"
            2 -> "판타지"
            3 -> "공포"
            4 -> "동물"
            5 -> "친구"
            6 -> "가족"
            7 -> "음식"
            8 -> "일"
            else -> "기타"
        }
    }

    enum class MotionIcon(

        val color: Int,

        val icon: Int

    ) {

        JOY(1, R.drawable.feeling_m_joy),

        SAD(2, R.drawable.feeling_m_sad)

    }

}