package com.recodream_aos.recordream.presentation.login // ktlint-disable package-name
// 로직 문제 없음
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.recodream_aos.recordream.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _signUpSuccess = MutableStateFlow(false)
    val signUpSuccess: StateFlow<Boolean> get() = _signUpSuccess

    private val fcmToken = MutableLiveData<String>()

    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (token != null) {
            Log.d("*****KAKAOLOGIN/SUCCESS*****", "카카오계정 로그인 성공")
            getUserToken(token.accessToken, fcmToken.value.toString())
        } else if (error != null) {
            Log.d("*****KAKAOLOGIN/FAILURE*****", "$error")
        }
    }

    init {
        getFCMToken()
    }

    fun getKaKaoCallback(): (OAuthToken?, Throwable?) -> Unit {
        return kakaoLoginCallback
    }

    private fun isSignUpSuccess() {
        _signUpSuccess.value = true
    }

    private fun getUserToken(kakaoToken: String, fcmToken: String) {
        viewModelScope.launch {
            if (authRepository.postLogin(
                    kakaoToken,
                    fcmToken,
                )
            ) {
                isSignUpSuccess()
            }
        }
    }

    fun getFCMToken() {
        viewModelScope.launch {
            authRepository.getFcmToken { getFcmToken -> fcmToken.value = getFcmToken }
        }
    }
}
