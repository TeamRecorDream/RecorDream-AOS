package com.team.recordream.presentation.storagy

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.team.recordream.R
import com.team.recordream.databinding.ItemHashTagBinding

class HashTag(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    private var binding: ItemHashTagBinding? = null

    private lateinit var inflater: LayoutInflater

    init {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(context)
        }
    }

    fun setTips(tag: List<Int>) {
        this.removeAllViews()
        for (i in tag.indices) {
            binding =
                ItemHashTagBinding.inflate(LayoutInflater.from(context), this, false).apply {
                    tvStoragHashTag.setText(checkGenreList(tag[i]))
                }
            addView(binding?.root)
        }
    }

    fun checkGenreList(genre: Int) = when (genre) {
        1 -> R.string.tv_record_comedy
        2 -> R.string.tv_record_romance
        3 -> R.string.tv_record_fantasy
        4 -> R.string.tv_record_horror
        5 -> R.string.tv_record_animal
        6 -> R.string.tv_record_friend
        7 -> R.string.tv_record_family
        8 -> R.string.tv_record_food
        9 -> R.string.tv_record_work
        10 -> R.string.tv_record_etc
        else -> R.string.genre_name_nothing
    }
}
