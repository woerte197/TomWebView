package com.wangyang.tomwebview

import android.os.Bundle
import com.wangyang.base.BaseActivity
import com.wangyang.common.WebViewService
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadUrl.setOnClickListener {
//            val webViewServiceImpl = ServiceLoader.load(WebViewService::class.java).iterator().next()
            val webViewServiceImpl = ServiceLoader.load(WebViewService::class.java).iterator().next()
            webViewServiceImpl.startWebViewActivity("https://www.baidu.com/",this)
//            webViewServiceImpl.startWebViewActivity("https://www.baidu.com/",this)
        }
    }
}
