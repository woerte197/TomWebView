package com.wangyang.base

import android.app.Application
import com.wangyang.loadsir.callback.*
import com.wangyang.loadsir.core.LoadSir

abstract class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
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