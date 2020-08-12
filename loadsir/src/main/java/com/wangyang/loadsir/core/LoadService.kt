package com.wangyang.loadsir.core

import android.content.Context
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.wangyang.loadsir.callback.Callback
import com.wangyang.loadsir.callback.SuccessCallback

/**
 * Description:TODO
 * Create Time:2017/9/6 10:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
class LoadService<T> internal constructor(
    private val convertor: Convertor<T>?,
    private val loadLayout: LoadLayout,
    builder: LoadSir.Builder
) {

    init {
        initCallback(builder)
    }

    private fun initCallback(builder: LoadSir.Builder) {
        val callbacks =
            builder.getCallbacks()
        val defaultCallback =
            builder.defaultCallback
        if (callbacks.isNotEmpty()) {
            callbacks.forEach {
                loadLayout.setupCallback(it)
            }
        }
        Handler().post {
            if (defaultCallback != null) {
                loadLayout.showCallback(defaultCallback)
            }
        }
    }
    fun getLoadLayout(): LoadLayout? {
        return loadLayout
    }

    fun showSuccess() {
        loadLayout.showCallback(SuccessCallback::class.java)
    }

    fun showCallback(callback: Class<out Callback?>) {
        loadLayout.showCallback(callback)
    }

    fun showWithConvertor(t: T) {
        requireNotNull(convertor) { "You haven't set the Convertor." }
        loadLayout.showCallback(convertor.map(t))
    }

    val currentCallback: Class<out Callback>
        get() = loadLayout.currentCallback!!

    /**
     * obtain rootView if you want keep the toolbar in Fragment
     *
     * @since 1.2.2
     */
    @Deprecated("")
    fun getTitleLoadLayout(
        context: Context?,
        rootView: ViewGroup,
        titleView: View?
    ): LinearLayout {
        val newRootView = LinearLayout(context)
        newRootView.orientation = LinearLayout.VERTICAL
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        newRootView.layoutParams = layoutParams
        rootView.removeView(titleView)
        newRootView.addView(titleView)
        newRootView.addView(loadLayout, layoutParams)
        return newRootView
    }

    /**
     * modify the callback dynamically
     *
     * @param callback  which callback you want modify(layout, event)
     * @param transport a interface include modify logic
     * @since 1.2.2
     */
    fun setCallBack(
        callback: Class<out Callback?>,
        transport: Transport
    ): LoadService<T> {
        loadLayout.setCallBack(callback, transport)
        return this
    }

}