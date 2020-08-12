package com.wangyang.tom_web.webviewclient

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class TomWebViewClient(private val  webViewClientCallBack: WebViewClientCallBack) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        webViewClientCallBack.onPageStarted(view, url, favicon)
    }


    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        webViewClientCallBack.onPageFinished(view, url)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        webViewClientCallBack.onReceivedError(view, request, error)
    }

}

interface WebViewClientCallBack {

    fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)

    fun onPageFinished(view: WebView?, url: String?)

    fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    )
}