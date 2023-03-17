package com.recodream_aos.recordream.presentation.storagy

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.ItemHashTagBinding

class HashTag(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    private var binding: ItemHashTagBinding? = null

    private lateinit var inflater: LayoutInflater

    init {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(context)
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
        0 -> R.string.tv_record_comedy
        1 -> R.string.tv_record_romance
        2 -> R.string.tv_record_fantasy
        3 -> R.string.tv_record_horror
        4 -> R.string.tv_record_animal
        5 -> R.string.tv_record_friend
        6 -> R.string.tv_record_family
        7 -> R.string.tv_record_food
        8 -> R.string.tv_record_work
        else -> R.string.tv_record_etc
    }
}