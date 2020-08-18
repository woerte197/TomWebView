package com.wangyang.tom_web

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import com.google.gson.Gson
import com.wangyang.tom_web.webviewprocess.WebViewProcessCommandDispatcher
import com.wangyang.tom_web.webviewprocess.webchromeclient.TomWebChromeClient
import com.wangyang.tom_web.webviewprocess.websettings.WebViewSettings
import com.wangyang.tom_web.webviewprocess.webviewclient.TomWebViewClient
import com.wangyang.tom_web.webviewprocess.webviewjavascriptinterface.JavaScriptCommand
import com.wangyang.tom_web.webviewprocess.webviewjavascriptinterface.TomWebViewJavaScriptInterface
import kotlinx.android.synthetic.main.fragment_web_view.view.*

class TomWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {
    var webViewCallBack: WebViewCallBack? = null
        set(value) {
            field = value
            initClient()
        }

    private fun initClient() {
        webChromeClient = TomWebChromeClient(webViewCallBack)
        webViewClient = TomWebViewClient(webViewCallBack)
        addJavascriptInterface(TomWebViewJavaScriptInterface(context, object : JavaScriptCommand {
            override fun exec(context: Context, commandBean: CommandBean) {
                webViewCallBack?.exec(context, commandBean, this@TomWebView)
            }
        }), "webview")
    }

    var mHeaders: MutableMap<String, String>? = null

    init {
        WebViewSettings.ins.initSettings(this)
        WebViewProcessCommandDispatcher.ins.initAidlConnection()
    }

    fun handleCallBack(callBackName: String?, response: String?) {
        val jsCode = "javascript:dj.callback('$callBackName','$response')"
        webView.post {
            loadUrl(jsCode)
        }
    }

    override fun loadUrl(url: String?) {
        if (webViewCallBack == null) {
            throw NullPointerException("Must be init wevViewCallBack")
        }
        if (mHeaders != null) {
            super.loadUrl(url, mHeaders)
        } else {
            super.loadUrl(url)
        }
    }

    fun loadHtml(htmlUrI: String) {
        loadUrl(htmlUrI)
    }

    fun loadJS(cmd: String, params: Any) {
        val trigger = "javascript:" + cmd + "(" + Gson().toJson(params) + ")"
        evaluateJavascript(trigger, null)
    }


}