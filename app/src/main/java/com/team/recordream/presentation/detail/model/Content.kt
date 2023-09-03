package com.team.recordream.presentation.detail.model

import com.team.recordream.presentation.detail.DetailViewModel.Companion.CONTENT_CATEGORY_DREAM_RECORD

data class Content(
    val category: String,
    val content: String,
    val isRecorded: Boolean,
) {
    fun showProgressBar(): Boolean =
        isRecorded && category == CONTENT_CATEGORY_DREAM_RECORD
}
