package com.wangyang.tomwebview

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.auto.service.AutoService
import com.wangyang.base.BaseApp
import com.wangyang.tom_web.IMainProcessToWebViewProcessCallBack
import com.wangyang.tom_web.mainprocess.ICommand

@AutoService(*[ICommand::class])
class ToastCommand : ICommand {
    override fun execute(
        jsonParams: MutableMap<String, String>?,
        callBack: IMainProcessToWebViewProcessCallBack?
    ) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(BaseApp.application, jsonParams?.get("message"), Toast.LENGTH_SHORT).show()
        }
    }

    override fun name(): String = "showToast"

}