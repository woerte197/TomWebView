package com.wangyang.tom_web.webchromeclient

import android.webkit.WebChromeClient
import android.webkit.WebView
import com.wangyang.tom_web.WebViewClientCallBack

class TomWebChromeClient(private val webViewClientCallBack: WebViewClientCallBack) : WebChromeClient() {
    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        webViewClientCallBack.onReceivedTitle(view, title)
    }

}
