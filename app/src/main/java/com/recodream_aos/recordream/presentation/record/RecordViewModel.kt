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

    init {
        _date.value = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN))
    }

    fun initDate() = DatePickerDialog.OnDateSetListener { view, year, month, day ->
        _date.value = "$year-${modifyDateUnits(month + ONE)}-${modifyDateUnits(day)}"
    }

    private fun modifyDateUnits(month: Int): String {
        if (month < TEN) return ZERO + month.toString()
        return month.toString()
    }

    companion object {
        const val ZERO = "0"
        const val ONE = 1
        const val TEN = 10
        const val BLANK = ""
        const val DATE_PATTERN = "yyyy-MM-dd"
    }
}
