package com.wangyang.tom_web.webViewProcess.webchromeclient

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.wangyang.tom_web.WebViewCallBack

class TomWebChromeClient(private val webViewCallBack: WebViewCallBack?) : WebChromeClient() {
    companion object {
        private const val TAG = "TomWebChromeClient"
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        webViewCallBack?.onReceivedTitle(view, title)
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        Log.e(Companion.TAG, "onConsoleMessage: ${consoleMessage?.message()}")
        return super.onConsoleMessage(consoleMessage)
    }


}
