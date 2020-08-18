package com.wangyang.tomwebview

import android.content.ComponentName
import android.content.Intent
import com.google.auto.service.AutoService
import com.wangyang.base.BaseApp
import com.wangyang.tom_web.IMainProcessToWebViewProcessCallBack
import com.wangyang.tom_web.mainprocess.ICommand

@AutoService(*[ICommand::class])
class OpenPageCommand : ICommand {
    override fun execute(
        jsonParams: MutableMap<String, String>?,
        callBack: IMainProcessToWebViewProcessCallBack?
    ) {
        val targetClass = jsonParams?.get("targetClass")
        val intent = Intent()
        intent.component = ComponentName(BaseApp.application!!, targetClass!!)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        BaseApp.application?.startActivity(intent)
    }

    override fun name() = "openPage"

}