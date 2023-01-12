package com.recodream_aos.recordream.presentation.splash // ktlint-disable package-name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recodream_aos.recordream.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _isLoginSuccess = MutableStateFlow(false)
    val isLoginSuccess: StateFlow<Boolean> get() = _isLoginSuccess

    private fun isLoginSuccess() {
        _isLoginSuccess.value = true
    }

    fun tryLogin() {
        viewModelScope.launch {
            if (authRepository.postToken()) isLoginSuccess()
        }
    }
}
