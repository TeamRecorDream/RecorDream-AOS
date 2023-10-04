package com.team.recordream.presentation.mypage

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.recordream.R
import com.team.recordream.data.datasource.local.AuthTokenStorage
import com.team.recordream.data.entity.remote.request.RequestAlamToggle
import com.team.recordream.data.entity.remote.request.RequestNickName
import com.team.recordream.data.entity.remote.request.RequestPushAlam
import com.team.recordream.domain.repository.AuthRepository
import com.team.recordream.domain.repository.MypageUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val mypageUserRepository: MypageUserRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    val userName = MutableLiveData<String>()

    var setDay: String = "AM"
    var setHour: Int = 0
    var setMinute: Int = 0

    private val _state: MutableLiveData<ViewState> = MutableLiveData(ViewState.Loading)
    val state: LiveData<ViewState> get() = _state

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    private val _settingTime = MutableLiveData<String?>()
    val settingTime: MutableLiveData<String?> get() = _settingTime

    private val _isShow = MutableLiveData<String>()
    val isShow: LiveData<String> get() = _isShow

    private val _isSuccessWithdraw = MutableLiveData<Boolean>()
    val isSuccessWithdraw: LiveData<Boolean> = _isSuccessWithdraw

    val saveTime = MutableLiveData<Boolean?>()

    private lateinit var timeUnits: List<String>

    lateinit var switchState: SharedPreferences

    fun getUser() {
        _state.value = ViewState.Loading
        viewModelScope.launch {
            userName.value = mypageUserRepository.getUser()?.data?.nickname
            _userEmail.value = mypageUserRepository.getUser()?.data?.email
            _settingTime.value = mypageUserRepository.getUser()?.data?.time
            formatDate()
            _state.value = ViewState.Success
        }
    }

    fun formatDate() {
        val day = _settingTime.value
        if (day.isNullOrBlank()) {
            return
        }
        timeUnits = day.split(" ", ":")
        setDay = timeUnits[0]
        setHour = timeUnits[1].toInt()
        setMinute = timeUnits[2].toInt()
    }

    fun postPushAlam() {
        viewModelScope.launch {
            mypageUserRepository.postPushAlam(RequestPushAlam(_isShow.value.toString()))
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

    fun patchAlamToggle(alamToggle: Boolean) {
        viewModelScope.launch {
            mypageUserRepository.patchAlamToggle(RequestAlamToggle(alamToggle))
        }
    }

    fun setShowDay() {
        val formatHour = String.format("%02d", setHour)
        val formatMinute = String.format("%02d", setMinute)
        _isShow.value = String.format(
            "%s %s:%s",
            setDay,
            formatHour,
            formatMinute,
        )
    }

    fun userLogout() {
        authRepository.unLinkKakaoAccount { isSuccess -> initIsSuccessWithdraw(isSuccess) }
        postSignOut()
        deleteSharedPrefernceLog()
    }

    private fun deleteSharedPrefernceLog() {
        AuthTokenStorage.logout()
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

    sealed interface ViewState {
        object Success : ViewState
        object Loading : ViewState
        object Idle : ViewState
    }

    companion object {
        const val NICKNAME_BALNK = R.string.mypage_name_warning
    }
}
