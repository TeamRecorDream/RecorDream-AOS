package com.recodream_aos.recordream.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recodream_aos.recordream.data.entity.remote.request.RequestAlamToggle
import com.recodream_aos.recordream.data.entity.remote.request.RequestPushAlam
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

    private val _alamToggle = MutableLiveData<Boolean>()
    val alamToggle: LiveData<Boolean> get() = _alamToggle

    private val _isShow = MutableLiveData<String>()
    val isShow: LiveData<String> get() = _isShow


    fun getUser() {
        viewModelScope.launch {
            userName.value = mypageUserRepository.getUser()?.data?.nickname
            _userEmail.value = mypageUserRepository.getUser()?.data?.email
        }
    }

    fun postPushAlam() {
        viewModelScope.launch {
            mypageUserRepository.postPushAlam(RequestPushAlam(isShow.value.toString()))
//                MypageUserRepository
            Log.d("mypageviewmodel", "postPushAlam: ")
        }
    }

    fun patchAlamToggle(alamToggle: Boolean) {
        viewModelScope.launch {
            mypageUserRepository.patchAlamToggle(RequestAlamToggle(alamToggle))
        }
    }

    fun checkAlamToggle(isActive: Boolean) {
        _alamToggle.value = isActive
    }

    fun setIsShow(day: String, h: Int, m: Int) {
        var formatHour = String.format("%02d", h)
        var formatMinute = String.format("%02d", m)
        _isShow.value = String.format(
            //todo %02s로 하면 왜 안됨?
            "%s %s:%s",
            day, formatHour, formatMinute
        )
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
