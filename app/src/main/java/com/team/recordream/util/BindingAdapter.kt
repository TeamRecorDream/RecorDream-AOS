package com.team.recordream.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.team.recordream.R
import com.team.recordream.presentation.common.model.PlayButtonState
import com.team.recordream.presentation.common.model.PlayButtonState.*
import com.team.recordream.presentation.record.model.Emotion
import com.team.recordream.presentation.record.model.Emotion.*
import com.team.recordream.presentation.record.recording.uistate.RecordButtonState
import com.team.recordream.presentation.record.recording.uistate.RecordButtonState.*

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
    @BindingAdapter("isProgressBarVisible")
    fun isProgressBarVisible(view: View, isVisible: Boolean) {
        when (isVisible) {
            true -> view.visibility = View.VISIBLE
            false -> view.visibility = View.GONE
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
        when (Emotion.getValue(emotionId)) {
            JOY -> R.drawable.feeling_m_joy.setImageWithGlide(imageview)
            SAD -> R.drawable.feeling_m_sad.setImageWithGlide(imageview)
            SCARY -> R.drawable.feeling_m_scary.setImageWithGlide(imageview)
            STRANGE -> R.drawable.feeling_m_strange.setImageWithGlide(imageview)
            SHY -> R.drawable.feeling_m_shy.setImageWithGlide(imageview)
            else -> R.drawable.feeling_m_blank.setImageWithGlide(imageview)
        }
    }

    @JvmStatic
    @BindingAdapter("setEmotionBackground")
    fun setEmotionBackground(view: View, emotionId: Int) {
        when (Emotion.getValue(emotionId)) {
            JOY -> view.setBackgroundResource(R.drawable.list_yellow)
            SAD -> view.setBackgroundResource(R.drawable.list_blue)
            SCARY -> view.setBackgroundResource(R.drawable.list_red)
            STRANGE -> view.setBackgroundResource(R.drawable.list_purple)
            SHY -> view.setBackgroundResource(R.drawable.list_pink)
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
    @BindingAdapter("setBackground")
    fun setBackground(view: View, image: Int) {
        view.setBackgroundResource(image)
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

    @JvmStatic
    @BindingAdapter("srcRecorderState")
    fun setImageByRecorderState(imageview: ImageView, recorderState: Boolean) {
        when (recorderState) {
            false -> R.drawable.icn_start.setImageWithGlide(imageview)
            true -> R.drawable.icn_stop.setImageWithGlide(imageview)
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
