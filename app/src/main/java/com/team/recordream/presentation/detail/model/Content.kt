package com.team.recordream.presentation.detail.model

import com.team.recordream.presentation.common.model.PlayButtonState
import com.team.recordream.presentation.detail.DetailViewModel.Companion.CONTENT_CATEGORY_DREAM_RECORD

data class Content(
    val category: String,
    val content: String,
    val isRecorded: Boolean,
    val state: PlayButtonState,
) {
    fun showProgressBar(): Boolean =
        isRecorded && category == CONTENT_CATEGORY_DREAM_RECORD
}
