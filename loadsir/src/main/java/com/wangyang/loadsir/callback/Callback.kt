package com.wangyang.loadsir.callback

import android.content.Context
import android.view.View
import java.io.*

/**
 * Description:TODO
 * Create Time:2017/9/2 17:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
abstract class Callback : Serializable {
    private var rootView: View? = null
    private var context: Context? = null
    private var onReloadListener: OnReloadListener? = null

    /**
     * if return true, the successView will be visible when the view of callback is attached.
     */
    var successVisible = false

    constructor() {}
    internal constructor(
        view: View?,
        context: Context?,
        onReloadListener: OnReloadListener?
    ) {
        rootView = view
        this.context = context
        this.onReloadListener = onReloadListener
    }

    fun setCallback(
        context: Context?,
        onReloadListener: OnReloadListener?
    ): Callback {
        this.context = context
        this.onReloadListener = onReloadListener
        return this
    }

    fun getRootView(): View? {
        val resId = onCreateView()
        if (resId == 0 && rootView != null) {
            return rootView
        }
        if (onBuildView(context) != null) {
            rootView = onBuildView(context)
        }
        if (rootView == null) {
            rootView = View.inflate(context, onCreateView(), null)
        }
        rootView?.setOnClickListener {
            if (!onReloadEvent(context, rootView)) {
                onReloadListener?.onReload(it)
            }
        }
        onViewCreate(context, rootView)
        return rootView
    }

    protected open fun onBuildView(context: Context?): View? {
        return null
    }

    @Deprecated(
        "Use {@link #onReloadEvent(Context context, View view)} instead.",
        ReplaceWith("false")
    )
    protected fun onRetry(
        context: Context?,
        view: View?
    ): Boolean {
        return false
    }

    protected open fun onReloadEvent(
        context: Context?,
        view: View?
    ): Boolean {
        return false
    }

    fun copy(): Callback {
        val bao = ByteArrayOutputStream()
        val oos: ObjectOutputStream
        var obj: Any? = null
        try {
            oos = ObjectOutputStream(bao)
            oos.writeObject(this)
            oos.close()
            val bis = ByteArrayInputStream(bao.toByteArray())
            val ois = ObjectInputStream(bis)
            obj = ois.readObject()
            ois.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return obj as Callback
    }

    /**
     * @since 1.2.2
     */
    fun obtainRootView(): View? {
        if (rootView == null) {
            rootView = View.inflate(context, onCreateView(), null)
        }
        return rootView
    }

    interface OnReloadListener : Serializable {
        fun onReload(v: View?)
    }

    protected abstract fun onCreateView(): Int

    /**
     * Called immediately after [.onCreateView]
     *
     * @since 1.2.2
     */
    protected open fun onViewCreate(
        context: Context?,
        view: View?
    ) {
    }

    /**
     * Called when the rootView of Callback is attached to its LoadLayout.
     *
     * @since 1.2.2
     */
    open fun onAttach(context: Context?, view: View?) {}

    /**
     * Called when the rootView of Callback is removed from its LoadLayout.
     *
     * @since 1.2.2
     */
    open fun onDetach() {}
}