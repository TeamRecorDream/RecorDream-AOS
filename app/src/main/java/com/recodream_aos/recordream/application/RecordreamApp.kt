package com.recodream_aos.recordream.application // ktlint-disable package-name

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.util.TimberDebugTree
import io.grpc.android.BuildConfig
import timber.log.Timber

class RecordreamApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKakaoSdk()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(TimberDebugTree())
        }
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(
            this,
            this.getString(R.string.appKey_init_kakao)
        )
    }
}
