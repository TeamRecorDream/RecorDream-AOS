package com.recodream_aos.recordream.presentation.document2

import androidx.lifecycle.ViewModel
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.presentation.document2.DocumentssViewModel.Emotion.ALL
import com.recodream_aos.recordream.presentation.document2.DocumentssViewModel.Emotion.JOY
import com.recodream_aos.recordream.presentation.document2.DocumentssViewModel.Emotion.SAD
import com.recodream_aos.recordream.presentation.document2.DocumentssViewModel.Emotion.SCARY
import com.recodream_aos.recordream.presentation.document2.DocumentssViewModel.Emotion.SHY
import com.recodream_aos.recordream.presentation.document2.DocumentssViewModel.Emotion.STRANGE
import com.recodream_aos.recordream.presentation.document2.model.Dummy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DocumentssViewModel : ViewModel() {
    private val _background: MutableStateFlow<Int> = MutableStateFlow(0)
    val background: StateFlow<Int> get() = _background

    private val _icon: MutableStateFlow<Int> = MutableStateFlow(0)
    val icon: StateFlow<Int> get() = _icon

    private val _date: MutableStateFlow<String> = MutableStateFlow("")
    val date: StateFlow<String> get() = _date

    private val _title: MutableStateFlow<String> = MutableStateFlow("")
    val title: StateFlow<String> get() = _title

    private val _tags: MutableStateFlow<List<Int>> = MutableStateFlow(listOf())
    val tags: StateFlow<List<Int>> get() = _tags

    init {
        _background.value = findBackgroundById(DATA.emotion)
        _icon.value = findIconById(DATA.emotion)
        _date.value = DATA.date
        _title.value = DATA.title
        _tags.value = DATA.genre
    }

    private fun findIconById(id: Int): Int {
        return when (Emotion.getValue(id)) {
            JOY -> R.drawable.feeling_l_joy
            SAD -> R.drawable.feeling_l_sad
            SCARY -> R.drawable.feeling_l_scary
            STRANGE -> R.drawable.feeling_l_strange
            SHY -> R.drawable.feeling_l_shy
            ALL -> R.drawable.feeling_l_blank
        }
    }

    private fun findBackgroundById(id: Int): Int {
        return when (Emotion.getValue(id)) {
            JOY -> R.drawable.background_yellow
            SAD -> R.drawable.background_blue
            SCARY -> R.drawable.background_red
            STRANGE -> R.drawable.background_purple
            SHY -> R.drawable.background_pink
            ALL -> R.drawable.background_white
        }
    }

    private enum class Emotion {
        JOY, SAD, SCARY, STRANGE, SHY, ALL;

        companion object {

            fun getValue(emotionId: Int): Emotion = values().find { emotion ->
                emotion.ordinal + 1 == emotionId
            } ?: ALL
        }
    }

    companion object {
        private val DATA =
            Dummy(
                writer = "세훈",
                date = "2022/06/26 SUN",
                title = "오늘 친구들이랑 피자도 먹고 진짜 재밌는 일이 많은 꿈을 꿨다.",
                voice = null,
                content = "아브라카다브라",
                emotion = 1,
                genre = listOf(1, 2, 3),
                note = "안녕하시무니까",
            )
    }
}
