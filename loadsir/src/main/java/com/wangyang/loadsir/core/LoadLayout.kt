package com.wangyang.loadsir.core

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.wangyang.loadsir.LoadSirUtil
import com.wangyang.loadsir.callback.Callback
import com.wangyang.loadsir.callback.Callback.OnReloadListener
import com.wangyang.loadsir.callback.SuccessCallback
import java.util.*

/**
 * Description:TODO
 * Create Time:2017/9/2 17:02
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
class LoadLayout(context: Context) : FrameLayout(context) {
    private val TAG = javaClass.simpleName
    private val callbacks: MutableMap<Class<out Callback>, Callback?> =
        HashMap()
    private var mContext: Context? = null
    private var onReloadListener: OnReloadListener? = null
    private var preCallback: Class<out Callback>? = null
    var currentCallback: Class<out Callback>? = null
        private set

    constructor(context: Context, onReloadListener: OnReloadListener?) : this(context) {
        this.mContext = context
        this.onReloadListener = onReloadListener
    }

    fun setupSuccessLayout(callback: Callback) {
        addCallback(callback)
        val successView = callback.getRootView()
        successView!!.visibility = View.INVISIBLE
        addView(
            successView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        currentCallback = SuccessCallback::class.java
    }

    fun setupCallback(callback: Callback) {
        val cloneCallback = callback.copy()
        cloneCallback.setCallback(mContext, onReloadListener)
        addCallback(cloneCallback)
    }

    fun addCallback(callback: Callback?) {
        if (!callbacks.contains(callback!!::class.java)) {
            callbacks[callback::class.java] = callback
        }
    }

    fun showCallback(callback: Class<out Callback>) {
        checkCallbackExist(callback)
        if (LoadSirUtil.isMainThread) {
            showCallbackView(callback)
        } else {
            postToMainThread(callback)
        }
    }

    private fun postToMainThread(status: Class<out Callback>) {
        post { showCallbackView(status) }
    }

    private fun showCallbackView(status: Class<out Callback>) {
        if (preCallback != null) {
            if (preCallback == status) {
                return
            }
            callbacks[preCallback!!]!!.onDetach()
        }
        if (childCount > 1) {
            removeViewAt(CALLBACK_CUSTOM_INDEX)
        }
        callbacks.keys.forEach { key ->
            if (key == status) {
                val successCallback = callbacks[SuccessCallback::class.java] as SuccessCallback?
                if (key == SuccessCallback::class.java) {
                    successCallback?.show()
                } else {
                    successCallback?.showWithCallback(callbacks[key]!!.successVisible)
                    val rootView = callbacks[key]!!.getRootView()
                    addView(rootView)
                    callbacks[key]!!.onAttach(mContext, rootView)
                }
                preCallback = status
            }
        }
        currentCallback = status
    }

    fun setCallBack(callback: Class<out Callback>, transport: Transport) {
        checkCallbackExist(callback)
        transport.order(mContext, callbacks[callback]!!.obtainRootView())
    }

    private fun checkCallbackExist(callback: Class<out Callback>) {
        require(callbacks.containsKey(callback)) {
            "The Callback ${callback.simpleName} is nonexistent."
        }
    }

    companion object {
        private const val CALLBACK_CUSTOM_INDEX = 1
    }
}