package com.wangyang.loadsir.core

import com.wangyang.loadsir.LoadSirUtil
import com.wangyang.loadsir.callback.Callback
import com.wangyang.loadsir.callback.Callback.OnReloadListener
import com.wangyang.loadsir.target.ActivityTarget
import com.wangyang.loadsir.target.ITarget
import com.wangyang.loadsir.target.ViewTarget
import java.util.*

/**
 * Description:TODO
 * Create Time:2017/9/2 16:36
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
class LoadSir {
    private var builder: Builder

    private constructor() {
        builder = Builder()
    }

    private fun setBuilder(builder: Builder) {
        this.builder = builder
    }

    private constructor(builder: Builder) {
        this.builder = builder
    }

    fun register(target: Any): LoadService<*> {
        return register<Any>(target, null)
    }

    fun <T> register(target: Any, onReloadListener: OnReloadListener?): LoadService<*> {
        return register<Any>(target, onReloadListener, null)
    }

    @JvmOverloads
    fun <T> register(
        target: Any?,
        onReloadListener: OnReloadListener?,
        convertor: Convertor<T>?
    ): LoadService<*> {
        val targetContext =
            LoadSirUtil.getTargetContext(target, builder.getTargetContextList())
        val loadLayout = targetContext.replaceView(target, onReloadListener)
        return LoadService(convertor, loadLayout, builder)
    }

    class Builder {
        private val callbacks: MutableList<Callback> =
            ArrayList()
        private val targetContextList: MutableList<ITarget> =
            ArrayList()
        var defaultCallback: Class<out Callback>? =
            null
            private set

        fun addCallback(callback: Callback): Builder {
            callbacks.add(callback)
            return this
        }

        /**
         * @param targetContext
         * @return Builder
         * @since 1.3.8
         */
        fun addTargetContext(targetContext: ITarget): Builder {
            targetContextList.add(targetContext)
            return this
        }

        fun getTargetContextList(): List<ITarget> {
            return targetContextList
        }

        fun setDefaultCallback(defaultCallback: Class<out Callback>): Builder {
            this.defaultCallback = defaultCallback
            return this
        }

        fun getCallbacks(): List<Callback> {
            return callbacks
        }

        fun commit() {
            default!!.setBuilder(this)
        }

        fun build(): LoadSir {
            return LoadSir(this)
        }

        init {
            targetContextList.add(ActivityTarget())
            targetContextList.add(ViewTarget())
        }
    }

    companion object {
        @Volatile
        private var loadSir: LoadSir? = null
        val default: LoadSir?
            get() {
                if (loadSir == null) {
                    synchronized(LoadSir::class.java) {
                        if (loadSir == null) {
                            loadSir = LoadSir()
                        }
                    }
                }
                return loadSir
            }

        fun beginBuilder(): Builder {
            return Builder()
        }
    }
}