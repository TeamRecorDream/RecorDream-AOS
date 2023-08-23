package com.team.recordream.application

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.team.recordream.R
import com.team.recordream.data.datasource.local.AuthTokenStorage
import dagger.hilt.android.HiltAndroidApp
import io.grpc.android.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class RecordreamApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKakaoSdk()
        initSharedPreferences()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(
            this,
            this.getString(R.string.appKey_init_kakao),
        )
    }

    private fun initSharedPreferences() {
        AuthTokenStorage.init(applicationContext)
    }
}
