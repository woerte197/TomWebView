package com.wangyang.base

import android.app.Application
import com.wangyang.loadsir.callback.*
import com.wangyang.loadsir.core.LoadSir

abstract class BaseApp : Application() {
    companion object {
        var application: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .addCallback(TimeoutCallback())
            .addCallback(CustomCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }
}