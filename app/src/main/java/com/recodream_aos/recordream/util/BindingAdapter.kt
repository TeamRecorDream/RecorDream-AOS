package com.recodream_aos.recordream.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
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
