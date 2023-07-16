package com.recodream_aos.recordream.presentation.document2

import androidx.lifecycle.ViewModel
import com.recodream_aos.recordream.presentation.document2.model.DocumentUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DocumentssViewModel : ViewModel() {
    private val _record: MutableStateFlow<List<DocumentUiModel>> = MutableStateFlow(listOf())
    val record: StateFlow<List<DocumentUiModel>> = _record

    init {
        _record.value = listOf(
            DocumentUiModel(
                writer = "세훈",
                date = "2020",
                title = "빨리끝내ㅑ조",
                voice = null,
                content = "아브라카다브라",
                emotion = 2,
                genre = listOf(1, 2, 3),
                note = "안녕하시무니까",
            ),
            DocumentUiModel(
                writer = "세훈2",
                date = "202022222",
                title = "빨리끝내ㅑ조242424",
                voice = null,
                content = "아브라카다브라42424",
                emotion = 2,
                genre = listOf(1, 2, 3),
                note = "안녕하시무니까44444",
            ),
        )
    }
}
