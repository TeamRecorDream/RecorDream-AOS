package com.recodream_aos.recordream.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MypageViewModel : ViewModel() {

    val userName = MutableLiveData<String>()
    private val _amOrPm = MutableLiveData<String>("AM")
    val amOrPm: LiveData<String> get() = _amOrPm
    private val _hour = MutableLiveData<String>("00")
    val hour: LiveData<String> get() = _hour
    private val _minute = MutableLiveData<String>("00")
    val minute: LiveData<String> get() = _minute
    private val _isShow = MutableLiveData<String>()
    val isShow: LiveData<String> get() = _isShow

    fun setAmOrPm(str: String) {
        _amOrPm.value = str
    }

    fun setHour(h: Int) {
        _hour.value = String.format("%02d", h)
    }

    fun setMinute(m: Int) {
        _minute.value = String.format("%02d", m)
    }

    fun setIsShow() {
        _isShow.value = "$_hour"
    }

    fun editNickName() {
    }
}
