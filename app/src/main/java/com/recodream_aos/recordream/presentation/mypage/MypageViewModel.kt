package com.recodream_aos.recordream.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recodream_aos.recordream.domain.repository.MypageUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(private val mypageUserRepository: MypageUserRepository) :
    ViewModel() {

    val userName = MutableLiveData<String>()

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    private val _amOrPm = MutableLiveData<String>()
    val amOrPm: LiveData<String> get() = _amOrPm
    private val _hour = MutableLiveData<String>("00")
    val hour: LiveData<String> get() = _hour
    private val _minute = MutableLiveData<String>("00")
    val minute: LiveData<String> get() = _minute
    private val _isShow = MutableLiveData<String>()
    val isShow: LiveData<String> get() = _isShow


    fun getUser() {
        viewModelScope.launch {
            userName.value = mypageUserRepository.getUser()?.data?.nickname
            _userEmail.value = mypageUserRepository.getUser()?.data?.email
        }
    }

    fun setAmOrPm(str: String) {
        _amOrPm.value = str
    }

    fun setHour(h: Int) {
        _hour.value = String.format("%02d", h)
    }

    fun setMinute(m: Int) {
        _minute.value = String.format("%02d", m)
    }

    fun setIsShow(day: String, h: Int, m: Int) {
        _isShow.value = String.format(
            //todo %02s로 하면 왜 안됨?
            "%s %s:%s",
            //%2$s %2$s:%2s
            day, h, m
        )
        Log.d("mypageViewmodel", "setIsShow: ${isShow}")
    }

    fun editNickName() {
    }

//    private fun showNicknameWarning() {
//        if (binding.edtMypageName.text.isNullOrBlank()) {
//            // TODO: 이거 왜 int값임?
//            shortToast(R.string.mypage_name_warning)
//        }
//    }
}
