package com.wangyang.tom_web.webchromeclient

import android.webkit.WebChromeClient
import android.webkit.WebView

class TomWebChromeClient(private val tomWebChromeCallBack: TomWebChromeCallBack) : WebChromeClient() {
    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        tomWebChromeCallBack.onReceivedTitle(view, title)
    }

}

interface TomWebChromeCallBack {
    fun onReceivedTitle(view: WebView?, title: String?)
}