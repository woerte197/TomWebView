package com.wangyang.usercenter

import android.content.ComponentName
import android.content.Intent
import com.google.auto.service.AutoService
import com.wangyang.base.BaseApp
import com.wangyang.common.UserCenterService

@AutoService(*[UserCenterService::class])
class UserCenterServiceImpl : UserCenterService {
    override fun login(callback: (params:String) -> Unit) {
        val intent = Intent()
        intent.component = ComponentName(BaseApp.application!!, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        BaseApp.application?.startActivity(intent)
        callbackAny = callback
    }

    override fun isLogin(): Boolean {
        return login
    }

    companion object {
        var login = false
        var callbackAny: ((params:String) -> Unit)? = null
    }
}