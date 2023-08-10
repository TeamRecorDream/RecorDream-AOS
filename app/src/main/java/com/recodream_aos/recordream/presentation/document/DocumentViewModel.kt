package com.recodream_aos.recordream.presentation.document

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recodream_aos.recordream.data.entity.remote.response.ResponseDocument
import com.recodream_aos.recordream.domain.repository.DocumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : ViewModel() {
    private val _responses = MutableLiveData<ResponseDocument?>()
    val responses: LiveData<ResponseDocument?>
        get() = _responses

    var emotion = MutableLiveData<Int>()
    var date = MutableLiveData<String>()
    var title = MutableLiveData<String>()
    var genre1 = MutableLiveData<Int>()
    var genre2 = MutableLiveData<Int>()
    var genre3 = MutableLiveData<Int>()
    var diary = MutableLiveData<String>()

    fun initServer(recordId: String) {
        viewModelScope.launch {
            _responses.value = documentRepository.getDocument(recordId)?.data
        }
    }
}
