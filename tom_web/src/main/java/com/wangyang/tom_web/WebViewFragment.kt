package com.wangyang.tom_web

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.wangyang.common.WebViewConstant
import com.wangyang.loadsir.callback.Callback
import com.wangyang.loadsir.callback.ErrorCallback
import com.wangyang.loadsir.callback.LoadingCallback
import com.wangyang.loadsir.callback.SuccessCallback
import com.wangyang.loadsir.core.LoadService
import com.wangyang.loadsir.core.LoadSir
import com.wangyang.tom_web.webviewprocess.WebViewProcessCommandDispatcher
import kotlinx.android.synthetic.main.fragment_web_view.*


/**
 * WebViewFragment
 */
class WebViewFragment : Fragment(), WebViewCallBack,
    Callback.OnReloadListener {
    private var mUrl: String? = null
    private var mLoadService: LoadService<*>? = null
    private var mError: Boolean = false

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(WebViewConstant.WEB_URL, url)
                }
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mUrl = it.getString(WebViewConstant.WEB_URL)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_view, container, false)
        mLoadService = LoadSir.default?.register<Any>(view, this)
        return mLoadService?.getLoadLayout()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        webView.webViewCallBack = this
        webView.loadUrl("file:///android_asset/aidl.html")
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        mLoadService?.showCallback(LoadingCallback::class.java)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        if (mError) {
            mLoadService?.showCallback(ErrorCallback::class.java)
            mError = false
        } else {
            mLoadService?.showCallback(SuccessCallback::class.java)
        }
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        mError = true
    }

    override fun onReload(v: View?) {
        webView.reload()
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        if (activity is WebActivity) {
            (activity as WebActivity).updateTitle(title ?: "")
        }
    }

    override fun exec(context: Context?, commandBean: CommandBean, webView: TomWebView?) {
       WebViewProcessCommandDispatcher.ins.executeCommand(commandBean.cmd,commandBean.message,webView)
    }

    fun back(over: () -> Unit) {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            over.invoke()
        }
    }
}