package com.wangyang.tom_web.webviewprocess.websettings

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import com.wangyang.tom_web.BuildConfig

class WebViewSettings {
    companion object {
        val ins by lazy { WebViewSettings() }
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val netService: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWork = netService.getNetworkCapabilities(netService.activeNetwork)
        return netWork?.hasTransport(
            NetworkCapabilities.TRANSPORT_WIFI
        ) ?: false || netWork?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false || netWork?.hasTransport(
            NetworkCapabilities.TRANSPORT_ETHERNET
        ) ?: false
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initSettings(webView: WebView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw()
        }
        val mWebSettings = webView.settings
        mWebSettings.apply {
            val appCacheDir =
                webView.context.getDir("cache", Context.MODE_PRIVATE).path
            Log.i("WebSetting", "WebView cache dir: $appCacheDir")
            databasePath = appCacheDir
            javaScriptEnabled = true
            builtInZoomControls = false
            textZoom = 100
            databaseEnabled = true
            loadsImagesAutomatically = true
            blockNetworkImage = false //是否阻塞加载网络图片  协议http or https
            allowFileAccess = true //允许加载本地文件html  file协议
            javaScriptCanOpenWindowsAutomatically = true

            savePassword = false
            saveFormData = false
            loadWithOverviewMode = true
            useWideViewPort = true
            domStorageEnabled = true
            defaultTextEncodingName = "utf-8" //设置编码格式

            defaultFontSize = 16
            minimumFontSize = 10 //设置 WebView 支持的最小字体大小，默认为 8
            useWideViewPort = true

            /*  用户可以自己设置useragent 用户可以自己设置useragent*/
            userAgentString = "webprogress/build you agent info"


            setAppCacheEnabled(true)
            setSupportMultipleWindows(false)
            setSupportZoom(true)
            setNeedInitialFocus(true)
            setGeolocationEnabled(true)
            setAppCachePath(appCacheDir)
            setAppCacheMaxSize(1024 * 1024 * 80.toLong())

            cacheMode = if (isNetworkConnected(webView.context)) {
                WebSettings.LOAD_DEFAULT
            } else {
                WebSettings.LOAD_CACHE_ELSE_NETWORK
            }
            layoutAlgorithm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            } else {
                WebSettings.LayoutAlgorithm.NORMAL
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                allowFileAccessFromFileURLs = false //通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
                allowUniversalAccessFromFileURLs = false //允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
            }
        }


        // 硬件加速兼容性问题有点多
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
        // 硬件加速兼容性问题有点多
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }

    }

}