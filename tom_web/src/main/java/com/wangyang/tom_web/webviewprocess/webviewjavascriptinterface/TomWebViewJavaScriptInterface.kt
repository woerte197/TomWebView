package com.wangyang.tom_web.webviewprocess.webviewjavascriptinterface

import android.content.Context
import android.os.Handler
import android.util.Log
import android.webkit.JavascriptInterface
import com.wangyang.tom_web.CommandBean

class TomWebViewJavaScriptInterface(
    private val context: Context,
    private val javaScriptCommand: JavaScriptCommand
) {
    companion object {
        private val handler = Handler()
        private const val TAG = "TomWebViewJavaScriptInt"
    }


    @JavascriptInterface
    fun post(params: String) {
        handler.post {
            Log.e(Companion.TAG, "post: ")
            javaScriptCommand.exec(context, CommandBean().execute(params))
        }
    }
}

interface JavaScriptCommand {
    fun exec(context: Context, commandBean: CommandBean)
}