package com.recodream_aos.recordream.data.remote.response.storage

data class ResponseStore(
    val `data`: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val records: List<StoreCard>,
        val recordsCount: Int
    )
}