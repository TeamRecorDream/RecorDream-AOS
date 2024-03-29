package com.team.recordream.presentation.splash // ktlint-disable package-name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.recordream.domain.repository.AuthRepository
import com.team.recordream.presentation.splash.SplashViewModel.LoginState.FAIL
import com.team.recordream.presentation.splash.SplashViewModel.LoginState.IDLE
import com.team.recordream.presentation.splash.SplashViewModel.LoginState.SUCCESS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _isLoginSuccess = MutableStateFlow<LoginState>(IDLE)
    val isLoginSuccess: StateFlow<LoginState> get() = _isLoginSuccess

    init {
        tryLogin()
    }

    private fun tryLogin() {
        viewModelScope.launch {
            if (authRepository.postToken()) isLoginSuccess() else isLoginFailure()
            Timber.e("SplashViewModel2 : ${authRepository.postToken()}")
        }
    }

    private fun isLoginSuccess() {
        _isLoginSuccess.value = SUCCESS
    }

    private fun isLoginFailure() {
        _isLoginSuccess.value = FAIL
    }

    sealed interface LoginState {
        object SUCCESS : LoginState
        object FAIL : LoginState
        object IDLE : LoginState
    }
}
