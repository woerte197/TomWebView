package com.wangyang.tom_web.webviewprocess

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.wangyang.base.BaseApp
import com.wangyang.tom_web.IMainProcessToWebViewProcessCallBack
import com.wangyang.tom_web.IWebViewProcessToMainProcessInterface
import com.wangyang.tom_web.TomWebView
import com.wangyang.tom_web.mainprocess.IMainProcessCommandService

class WebViewProcessCommandDispatcher : ServiceConnection {
    var iWebViewProcessToMainProcessInterface: IWebViewProcessToMainProcessInterface? = null

    companion object {
        val ins by lazy { WebViewProcessCommandDispatcher() }
    }

    fun initAidlConnection() {
        val intent = Intent(BaseApp.application, IMainProcessCommandService::class.java)
        BaseApp.application?.bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        iWebViewProcessToMainProcessInterface = null
        initAidlConnection()
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        iWebViewProcessToMainProcessInterface =
            IWebViewProcessToMainProcessInterface.Stub.asInterface(service)
    }

    override fun onBindingDied(name: ComponentName?) {
        iWebViewProcessToMainProcessInterface = null
        initAidlConnection()
    }

    fun executeCommand(
        commandName: String?,
        params: MutableMap<String, String>?,
        webView: TomWebView?
    ) {
        iWebViewProcessToMainProcessInterface?.handleWebCommand(
            commandName,
            params,
            object : IMainProcessToWebViewProcessCallBack.Stub() {
                override fun onResult(callBackName: String?, response: String?) {
                    webView?.handleCallBack(callBackName, response)
                }
            })
    }
}