package com.wangyang.loadsir

import android.os.Looper
import com.wangyang.loadsir.target.ITarget

/**
 */
object LoadSirUtil {
    val isMainThread: Boolean
        get() = Looper.myLooper() == Looper.getMainLooper()

    fun getTargetContext(target: Any?, targetContextList: List<ITarget>): ITarget {
        targetContextList.forEach {
            if (it == target) return it
        }
        throw IllegalArgumentException("No TargetContext fit it")
    }
}