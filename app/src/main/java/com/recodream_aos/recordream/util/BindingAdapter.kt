package com.recodream_aos.recordream.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.presentation.record.recording.uistate.PlayButtonState
import com.recodream_aos.recordream.presentation.record.recording.uistate.PlayButtonState.*
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState
import com.recodream_aos.recordream.presentation.record.recording.uistate.RecordButtonState.*

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
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
    @BindingAdapter("glideSrcByRecordButtonState")
    fun glideSrcByRecordButtonState(imageview: ImageView, updatedState: RecordButtonState) {
        when (updatedState) {
            BEFORE_RECORDING -> R.drawable.icn_mic_start.setImageWithGlide(imageview)
            ON_RECORDING -> R.drawable.icn_mic_stop.setImageWithGlide(imageview)
            AFTER_RECORDING -> R.drawable.icn_mic_reset.setImageWithGlide(imageview)
        }
    }

    @JvmStatic
    @BindingAdapter("glideSrcByPlayButtonState")
    fun glideSrcByPlayButtonState(imageview: ImageView, updatedState: PlayButtonState) {
        when (updatedState) {
            RECORDER_PLAY -> R.drawable.icn_start.setImageWithGlide(imageview)
            RECORDER_STOP -> R.drawable.icn_stop.setImageWithGlide(imageview)
        }
    }

    private fun Int.setImageWithGlide(imageview: ImageView) {
        Glide.with(imageview.context)
            .load(this)
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
