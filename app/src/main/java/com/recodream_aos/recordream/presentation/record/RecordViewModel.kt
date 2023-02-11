package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecordViewModel : ViewModel() {

    private var _date = MutableStateFlow(BLANK)
    val date: StateFlow<String> get() = _date

    private var _time = MutableStateFlow(DEFAULT_TIME)
    val time: StateFlow<String> get() = _time

    val title = MutableStateFlow(BLANK)
    val content = MutableStateFlow(BLANK)
    var getRecordState = false

    init {
        initLocalDate()
    }

    fun initDate() = DatePickerDialog.OnDateSetListener { view, year, month, day ->
        _date.value = "$year-${modifyDateUnits(month + ONE)}-${modifyDateUnits(day)}"
    }

    private fun initLocalDate() {
        _date.value = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN))
    }

    private fun modifyDateUnits(month: Int): String {
        if (month < TEN) return ZERO + month.toString()
        return month.toString()
    }

    companion object {
        private const val ZERO = "0"
        private const val DEFAULT_TIME = "00:00"
        private const val ONE = 1
        private const val TEN = 10
        private const val BLANK = ""
        private const val DATE_PATTERN = "yyyy-MM-dd"
    }
}
