package com.recodream_aos.recordream.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: View, maxSelection: Boolean) {
        view.visibility = if (maxSelection) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("onChipClick")
    fun setClickEventOnChip(chip: Chip, onClick: View.OnClickListener) {
        chip.setOnClickListener(onClick)
    }

    @JvmStatic
    @BindingAdapter("selected")
    fun isSelected(view: View, isSelected: Boolean) {
        view.isSelected = isSelected
    }

    @JvmStatic
    @BindingAdapter("imgResId")
    fun setImageResId(imageview: ImageView, resId: Int) {
        imageview.setImageResource(resId)
    }

    @JvmStatic
    @BindingAdapter("glideSrc")
    fun glideSrc(imageview: ImageView, image: Int) {
        Glide.with(imageview.context)
            .load(image)
            .into(imageview)
    }

    @JvmStatic
    @BindingAdapter("imgGlideStr")
    fun setGlideImage(imageview: ImageView, image: String) {
        Glide.with(imageview.context)
            .load(image)
            .circleCrop()
            .into(imageview)
    }

    @JvmStatic
    @BindingAdapter("imgGlideInt")
    fun setGlideImage(imageview: ImageView, image: Int) {
        Glide.with(imageview.context)
            .load(image)
            .circleCrop()
            .into(imageview)
    }
}
