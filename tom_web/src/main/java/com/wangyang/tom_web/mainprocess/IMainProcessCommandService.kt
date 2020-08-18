package com.wangyang.tom_web.mainprocess

import android.app.Service
import android.content.Intent
import android.os.IBinder

class IMainProcessCommandService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return MainProcessCommandManager.ins.asBinder()
    }

}