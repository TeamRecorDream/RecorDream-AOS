package com.recodream_aos.recordream.data.datasource.remote

import android.util.Log
import com.recodream_aos.recordream.data.api.RecordService
import com.recodream_aos.recordream.data.entity.remote.response.ResponseVoice
import com.recodream_aos.recordream.util.extension.enqueueUtil
import okhttp3.MultipartBody
import javax.inject.Inject

class RecordDataSourceImpl @Inject constructor(
    private val recordService: RecordService,
) : RecordDataSource {
    override fun postVoice(
        onSuccess: (ResponseVoice) -> Unit,
        requestBody: MultipartBody.Part,
    ) {
        recordService.postVoice(requestBody).enqueueUtil(
            onSuccess = {
                Log.d("1231233", it.toString())
                onSuccess.invoke(it)
            },
            onFailInitNetwork = { Log.d("1231234", it.toString()) },
            onFailCallResponse = { Log.d("1231234", it) },
        )
    }
}
