package com.recodream_aos.recordream.presentation.login // ktlint-disable package-name

import android.os.Bundle
import androidx.activity.viewModels
import com.kakao.sdk.common.util.Utility
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("*****HASHKEY*****").d(Utility.getKeyHash(this))
        initViewModel()
    }

    private fun initViewModel() {
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this
    }
}
