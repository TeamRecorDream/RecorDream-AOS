package com.recodream_aos.recordream.application // ktlint-disable package-name

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.data.datasource.local.RecordreamSharedPreference
import com.recodream_aos.recordream.util.TimberDebugTree
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
            Timber.plant(TimberDebugTree())
        }
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(
            this,
            this.getString(R.string.appKey_init_kakao),
        )
    }

    private fun initSharedPreferences() {
        RecordreamSharedPreference.init(applicationContext)
    }
}
