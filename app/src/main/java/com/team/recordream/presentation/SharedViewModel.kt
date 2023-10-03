package com.team.recordream.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    // LiveData to trigger resetting ViewPager2
    private val _resetViewPagerEvent = MutableLiveData<Unit>()
    val resetViewPagerEvent: LiveData<Unit> get() = _resetViewPagerEvent

    fun triggerResetViewPager() {
        _resetViewPagerEvent.value = Unit
    }
}