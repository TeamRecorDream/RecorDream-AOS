package com.team.recordream.presentation.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.team.recordream.R
import com.team.recordream.databinding.HomeCardItemBinding
import com.team.recordream.presentation.home.model.UserRecords
import com.team.recordream.util.RecordreamMapping

class PagerViewHolder(
    private val binding: HomeCardItemBinding,
    itemClick: (UserRecords) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.onClick = itemClick
    }

    fun onBind(userRecords: UserRecords) {
        binding.userRecords = userRecords
        binding.tvHomeDate.text = userRecords.date
        binding.tvHomeCardTitle.text = userRecords.title
        binding.clHomeCard.setBackgroundResource(checkEmotionBackground(userRecords.emotion))
        binding.ivHomeEmoticon.setImageResource(checkEmotionIcon(userRecords.emotion))
        binding.tvHomeContent.text = userRecords.content

        if (userRecords.content.isBlank() && userRecords.voice) {
            binding.tvHomeContent.visibility = View.INVISIBLE
            binding.tvHomeMic.visibility = View.VISIBLE
            binding.ivHomeMic.visibility = View.VISIBLE
        } else {
            binding.tvHomeContent.visibility = View.VISIBLE
            binding.tvHomeMic.visibility = View.INVISIBLE
            binding.ivHomeMic.visibility = View.INVISIBLE
        }
        applyData(userRecords)
    }

    val recorDreamMapping = RecordreamMapping()

    private fun applyData(userRecords: UserRecords) {
        val applyGenre = userRecords.let { recorDreamMapping.genreMapping(it.genre) }

        binding.tvHomeCardTitle.text = userRecords.title
        binding.tvHomeDate.text = userRecords.date
        if (userRecords.genre.size == 1) {
            binding.tvHomeGenre1.text = "#${applyGenre[0]}"
        }
        if (userRecords.genre.size == 2) {
            binding.tvHomeGenre1.text = "#${applyGenre[0]}"
            binding.tvHomeGenre2.text = "#${applyGenre[1]}"
        }
        if (userRecords.genre.size == 3) {
            binding.tvHomeGenre1.text = "#${applyGenre[0]}"
            binding.tvHomeGenre2.text = "#${applyGenre[1]}"
            binding.tvHomeGenre3.text = "#${applyGenre[2]}"
        }
        when (userRecords.genre.size) {
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

    private fun checkEmotionIcon(color: Int) = when (color) {
        1 -> R.drawable.feeling_l_joy
        2 -> R.drawable.feeling_l_sad
        3 -> R.drawable.feeling_l_scary
        4 -> R.drawable.feeling_l_strange
        5 -> R.drawable.feeling_l_shy
        else -> R.drawable.feeling_l_blank
    }

    private fun checkEmotionBackground(color: Int) = when (color) {
        1 -> R.drawable.card_m_yellow
        2 -> R.drawable.card_m_blue
        3 -> R.drawable.card_m_red
        4 -> R.drawable.card_m_purple
        5 -> R.drawable.card_m_pink
        else -> R.drawable.card_m_white
    }
}