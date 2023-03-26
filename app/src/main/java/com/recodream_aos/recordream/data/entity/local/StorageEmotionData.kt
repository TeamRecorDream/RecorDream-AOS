package com.recodream_aos.recordream.data.entity.local

data class StorageEmotionData(
    val selectedImage: Int,
    val unSelectedImage: Int,
    val feelingText: String,
    var isSelected: Boolean = false,
)
