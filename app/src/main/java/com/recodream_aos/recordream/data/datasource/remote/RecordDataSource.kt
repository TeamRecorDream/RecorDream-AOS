package com.recodream_aos.recordream.data.datasource.remote

import com.recodream_aos.recordream.data.entity.remote.response.ResponseVoice
import okhttp3.MultipartBody

interface RecordDataSource {

    fun postVoice(onSuccess: (ResponseVoice) -> Unit, requestBody: MultipartBody.Part)
}
