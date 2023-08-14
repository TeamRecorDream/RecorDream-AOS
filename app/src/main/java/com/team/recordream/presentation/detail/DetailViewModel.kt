package com.team.recordream.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.recordream.R
import com.team.recordream.domain.repository.DocumentRepository
import com.team.recordream.presentation.record.uistate.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val documentRepository: DocumentRepository,
) : ViewModel() {
    private val _background: MutableStateFlow<Int> = MutableStateFlow(0)
    val background: StateFlow<Int> get() = _background

    private val _icon: MutableStateFlow<Int> = MutableStateFlow(0)
    val icon: StateFlow<Int> get() = _icon

    private val _date: MutableStateFlow<String> = MutableStateFlow("")
    val date: StateFlow<String> get() = _date

    private val _title: MutableStateFlow<String> = MutableStateFlow("")
    val title: StateFlow<String> get() = _title

    private val _tags: MutableStateFlow<List<Genre>> = MutableStateFlow(listOf())
    val tags: StateFlow<List<Genre>> get() = _tags

    fun updateDetailRecord(recordId: String) {
        viewModelScope.launch {
            documentRepository.getDetailRecord(recordId)
                .onSuccess {
                    _background.value = Emotion.findBackgroundById(it.emotion ?: 0)
                    _icon.value = Emotion.findIconById(it.emotion ?: 0)
                    _date.value = it.date
                    _title.value = it.title
                    findTagByGenreId(it.genre)
                }
                .onFailure {
                    Log.d("123123", it.message.toString())
                }
        }
    }

    private fun findTagByGenreId(genre: List<Int>) {
        when (genre.contains(0)) {
            true -> _tags.value = listOf(Genre.getValue(-1))
            false -> {
                _tags.value = genre.map {
                    Genre.getValue(it)
                }
            }
        }
    }

    private enum class Emotion {
        JOY, SAD, SCARY, STRANGE, SHY, ALL;

        companion object {
            fun findBackgroundById(id: Int): Int {
                return when (Emotion.getValue(id)) {
                    JOY -> R.drawable.background_yellow
                    SAD -> R.drawable.background_blue
                    SCARY -> R.drawable.background_red
                    STRANGE -> R.drawable.background_purple
                    SHY -> R.drawable.background_pink
                    ALL -> R.drawable.background_white
                }
            }

            fun findIconById(id: Int): Int {
                return when (Emotion.getValue(id)) {
                    JOY -> R.drawable.feeling_l_joy
                    SAD -> R.drawable.feeling_l_sad
                    SCARY -> R.drawable.feeling_l_scary
                    STRANGE -> R.drawable.feeling_l_strange
                    SHY -> R.drawable.feeling_l_shy
                    ALL -> R.drawable.feeling_l_blank
                }
            }

            fun getValue(emotionId: Int): Emotion = values().find { emotion ->
                emotion.ordinal + 1 == emotionId
            } ?: ALL
        }
    }
}
