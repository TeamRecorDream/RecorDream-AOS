package com.team.recordream.presentation.storagy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.R
import com.team.recordream.data.entity.remote.response.ResponseStorage
import com.team.recordream.databinding.ItemListStoreGridBinding
import com.team.recordream.presentation.storagy.adapter.StorageGridAdapter.StorageGridViewHolder.Companion.diffUtil
import com.team.recordream.util.DiffUtilItemCallback

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
        private val itemClick: (ResponseStorage.Record) -> (Unit),
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
            binding.gridContainerDreamTag.setTips(data.genre)

//            binding.gridContainerDreamTag.run {
//                val bindingDreamTag = {
//                    ItemListStoreTagBinding.inflate(LayoutInflater.from(binding.root.context))
//                }
//
//                removeAllViews()
//                data.genre.map { item ->
//                    bindingDreamTag().apply {
//                        tvStoreTag.text = checkGenreList(item)
//
//                    }
//                }.forEach {
//                    val margins =
//                        (binding.gridContainerDreamTag.layoutParams as ConstraintLayout.LayoutParams).apply {
//                            leftMargin = 10
//                            rightMargin = 10
//                        }
//
//                    addView(it.root)
//                }
//            }
        }

        companion object {
            val diffUtil = DiffUtilItemCallback<ResponseStorage.Record>(
                onItemsTheSame = { old, new -> old.id == new.id },
                onContentsTheSame = { old, new -> old == new },
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

        enum class MotionIcon(

            val color: Int,

            val icon: Int,

            ) {

            JOY(1, R.drawable.feeling_m_joy),
            SAD(2, R.drawable.feeling_m_sad),
            SCARY(3, R.drawable.feeling_m_scary),
            STRANGE(4, R.drawable.feeling_m_strange),
            SHY(5, R.drawable.feeling_m_shy),
            BLANK(6, R.drawable.feeling_m_blank),
        }
    }
}
