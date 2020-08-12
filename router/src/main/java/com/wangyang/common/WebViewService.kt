package com.wangyang.common

import android.content.Context
import androidx.fragment.app.Fragment

interface WebViewService {
    fun startWebViewActivity(url: String, context: Context)
    fun getWebViewFragment(url: String):Fragment
}

class WebViewConstant {
    companion object {
        const val WEB_URL = "web_url"
    }
}