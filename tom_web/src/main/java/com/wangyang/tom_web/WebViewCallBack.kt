package com.wangyang.tom_web

import android.content.Context
import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView

interface WebViewCallBack {

    fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)

    fun onPageFinished(view: WebView?, url: String?)

    fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    )
    fun onReceivedTitle(view: WebView?, title: String?)
    fun exec(
        context: Context?,
        commandBean: CommandBean,
        webView: WebView?
    )

}