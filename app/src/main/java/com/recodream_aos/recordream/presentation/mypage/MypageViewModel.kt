package com.recodream_aos.recordream.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.data.entity.remote.request.RequestAlamToggle
import com.recodream_aos.recordream.data.entity.remote.request.RequestNickName
import com.recodream_aos.recordream.data.entity.remote.request.RequestPushAlam
import com.recodream_aos.recordream.domain.repository.AuthRepository
import com.recodream_aos.recordream.domain.repository.MypageUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val mypageUserRepository: MypageUserRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    val userName = MutableLiveData<String>()

    val setDay = MutableLiveData<String>()
    val setHour = MutableLiveData<Int>()
    val setMinute = MutableLiveData<Int>()

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    private val _settingTime = MutableLiveData<String?>()
    val settingTime: MutableLiveData<String?> get() = _settingTime

    private val _isShow = MutableLiveData<String>()
    val isShow: LiveData<String> get() = _isShow

    private val _isSuccessWithdraw = MutableLiveData<Boolean>()
    val isSuccessWithdraw: LiveData<Boolean> = _isSuccessWithdraw

    val saveTime = MutableLiveData<Boolean?>()

    fun getUser() {
        viewModelScope.launch {
            userName.value = mypageUserRepository.getUser()?.data?.nickname
            _userEmail.value = mypageUserRepository.getUser()?.data?.email
            _settingTime.value = mypageUserRepository.getUser()?.data?.time
        }
    }

    fun postPushAlam() {
        viewModelScope.launch {
            mypageUserRepository.postPushAlam(RequestPushAlam(isShow.value.toString()))
        }
    }

    fun putUserName() {
        viewModelScope.launch {
            mypageUserRepository.putNickName(RequestNickName(userName.value.toString()))
        }
    }

    fun clickSaveTime(saveBtn: Boolean) {
        saveTime.value = saveBtn
    }

//    fun editNickName(nickName: String) {
//        if (nickName.isNullOrBlank()) {
//            userName.value = NICKNAME_BALNK.toString()
//        } else {
//            userName.value = nickName
//        }
//    }

    fun patchAlamToggle(alamToggle: Boolean) {
        viewModelScope.launch {
            mypageUserRepository.patchAlamToggle(RequestAlamToggle(alamToggle))
        }
    }

    fun setIsShow() {
        val formatHour = String.format("%02d", setHour)
        val formatMinute = String.format("%02d", setMinute)
        _isShow.value = String.format(
            // todo %02s로 하면 왜 안됨?
            "%s %s:%s",
            setDay,
            formatHour,
            formatMinute,
        )
    }

    fun userLogout() {
        authRepository.unLinkKakaoAccount { isSuccess -> initIsSuccessWithdraw(isSuccess) }
        postSignOut()
    }

    private fun initIsSuccessWithdraw(isSuccess: Boolean) {
        _isSuccessWithdraw.postValue(isSuccess)
    }

    private fun postSignOut() {
        viewModelScope.launch {
            authRepository.patchSignOut()
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            authRepository.deleteUser()
        }
    }

    companion object {
        const val NICKNAME_BALNK = R.string.mypage_name_warning
    }
}
