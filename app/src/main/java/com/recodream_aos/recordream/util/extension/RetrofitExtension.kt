package com.recodream_aos.recordream.util.extension

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <ResponseType> Call<ResponseType>.enqueueUtil(
    onSuccess: (ResponseType) -> Unit,
    onFailCallResponse: ((stateMessage: String) -> Unit)? = null,
    onFailInitNetwork: ((throwMessage: Throwable) -> Unit)? = null,
) {
    this.enqueue(object : Callback<ResponseType> {
        override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
            if (response.isSuccessful) {
                onSuccess.invoke(response.body() ?: return)
            } else {
                onFailCallResponse?.invoke(response.message())
            }
        }

        override fun onFailure(call: Call<ResponseType>, t: Throwable) {
            onFailInitNetwork?.invoke(t)
        }
    })
}
