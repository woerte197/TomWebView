package com.wangyang.loadsir.target

import com.wangyang.loadsir.callback.Callback.OnReloadListener
import com.wangyang.loadsir.core.LoadLayout

/**
 * Description:TODO
 * Create Time:2019/8/29 0029 下午 2:43
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
interface ITarget {
    /**
     *
     * @param target
     * @return
     * v1.3.8
     */
    override fun equals(target: Any?): Boolean

    /**
     * 1.removeView 2.确定LP 3.addView
     * @param target
     * @param onReloadListener
     * @return
     * v1.3.8
     */
    fun replaceView(target: Any?, onReloadListener: OnReloadListener?): LoadLayout
}