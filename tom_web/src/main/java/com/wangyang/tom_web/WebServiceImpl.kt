package com.wangyang.tom_web

import android.content.Context
import android.content.Intent
import com.google.auto.service.AutoService
import com.wangyang.common.WebViewConstant
import com.wangyang.common.WebViewService

@AutoService(*[WebViewService::class])
class WebServiceImpl : WebViewService {
    override fun startWebViewActivity(url: String, context: Context) {
        context.startActivity(
            Intent(
                context,
                WebActivity::class.java
            ).putExtra(WebViewConstant.WEB_URL, url)
        )
    }

    override fun getWebViewFragment(url: String): WebViewFragment {
        return WebViewFragment.newInstance(url)
    }

}