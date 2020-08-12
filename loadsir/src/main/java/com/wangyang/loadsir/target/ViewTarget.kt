package com.wangyang.loadsir.target

import android.view.View
import android.view.ViewGroup
import com.wangyang.loadsir.callback.Callback
import com.wangyang.loadsir.callback.SuccessCallback
import com.wangyang.loadsir.core.LoadLayout

/**
 * Description:TODO
 * Create Time:2019/8/29 0029 下午 2:44
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
class ViewTarget : ITarget {
    override fun equals(target: Any?): Boolean {
        return target is View
    }

    override fun replaceView(
        target: Any?,
        onReloadListener: Callback.OnReloadListener?
    ): LoadLayout {
        val oldContent = target as View
        val contentParent =   if (oldContent.parent != null) {
            oldContent.parent  as ViewGroup
        } else {
            null
        }
        var childIndex = 0
        val childCount = contentParent?.childCount ?: 0
        for (i in 0 until childCount) {
            if (contentParent?.getChildAt(i) === oldContent) {
                childIndex = i
                break
            }
        }
        contentParent?.removeView(oldContent)
        val oldLayoutParams = oldContent.layoutParams
        val loadLayout = LoadLayout(oldContent.context, onReloadListener)
        loadLayout.setupSuccessLayout(
            SuccessCallback(
                oldContent,
                oldContent.context,
                onReloadListener
            )
        )
        contentParent?.addView(loadLayout, childIndex, oldLayoutParams)
        return loadLayout
    }
}