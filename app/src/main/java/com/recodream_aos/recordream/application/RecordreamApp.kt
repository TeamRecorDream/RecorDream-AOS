package com.recodream_aos.recordream.application

import android.app.Application
import com.recodream_aos.recordream.BuildConfig
import com.recodream_aos.recordream.util.TimberDebugTree
import timber.log.Timber

class RecordreamApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(TimberDebugTree())
        }
    }
}
