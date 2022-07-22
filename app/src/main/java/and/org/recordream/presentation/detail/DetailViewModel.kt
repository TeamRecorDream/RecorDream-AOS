package and.org.recordream.presentation.detail

import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.response.ResponseDetailDreamRecord
import and.org.recordream.util.enqueueUtil
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel: ViewModel() {
    private val _detailResponse = MutableLiveData<ResponseDetailDreamRecord>()
    val detailResponse: LiveData<ResponseDetailDreamRecord> get() = _detailResponse

    fun getData(id: String) {
        val call = RecordreamClient.recorDreamServicee.getDetailRecord(id)

        call.enqueueUtil(
            onSuccess = { responseWrapper ->
                Log.d("dddddddddd", "${responseWrapper.status}")

                val data = responseWrapper.data
                data?.let {
                    _detailResponse.value = it
                }
            },
            onError = {
                Log.d("dddddddddd", "$it")
            }
        )
    }
}