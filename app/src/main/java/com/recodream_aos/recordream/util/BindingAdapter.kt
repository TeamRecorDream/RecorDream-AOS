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
        when (isVisible) {
            true -> view.visibility = View.VISIBLE
            false -> view.visibility = View.INVISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("isActivated")
    fun isActivated(view: View, isActivated: Boolean) {
        view.isActivated = isActivated
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
    @BindingAdapter("glideEmotionSrc")
    fun glideEmotionSrc(imageview: ImageView, emotionId: Int) {
        when (emotionId) {
            1 -> R.drawable.feeling_m_joy.setImageWithGlide(imageview)
            2 -> R.drawable.feeling_m_sad.setImageWithGlide(imageview)
            3 -> R.drawable.feeling_m_scary.setImageWithGlide(imageview)
            4 -> R.drawable.feeling_m_strange.setImageWithGlide(imageview)
            5 -> R.drawable.feeling_m_shy.setImageWithGlide(imageview)
            else -> R.drawable.feeling_m_blank.setImageWithGlide(imageview)
        }
    }

    @JvmStatic
    @BindingAdapter("setEmotionBackground")
    fun setEmotionBackground(view: View, emotionId: Int) {
        when (emotionId) {
            1 -> view.setBackgroundResource(R.drawable.list_yellow)
            2 -> view.setBackgroundResource(R.drawable.list_blue)
            3 -> view.setBackgroundResource(R.drawable.list_red)
            4 -> view.setBackgroundResource(R.drawable.list_purple)
            5 -> view.setBackgroundResource(R.drawable.list_pink)
            else -> view.setBackgroundResource(R.drawable.list_white)
        }
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
