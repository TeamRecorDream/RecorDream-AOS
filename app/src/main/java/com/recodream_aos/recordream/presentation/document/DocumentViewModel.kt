package com.recodream_aos.recordream.presentation.document

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DocumentViewModel : ViewModel() {
    var emotion = MutableLiveData<Int>()
    var date = MutableLiveData<String>()
    var title = MutableLiveData<String>()
    var genre1 = MutableLiveData<Int>()
    var genre2 = MutableLiveData<Int>()
    var genre3 = MutableLiveData<Int>()
    var diary = MutableLiveData<String>()
}
